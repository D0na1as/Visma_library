package visma.library.api.Controller;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import visma.library.api.Config.AvailabilityStatus;
import visma.library.api.Model.Book;
import visma.library.api.Model.Order;
import visma.library.api.Service.BookService;
import visma.library.api.Service.ClientService;
import visma.library.api.Utils.Utils;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/library")
public class MainController {

    @Autowired
    BookService bookService;
    @Autowired
    ClientService clientService;
    @Autowired
    private Utils utils;

    //Add Book
    @PostMapping("/book")
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book) throws Exception {
        book.setAvailb(true);
        bookService.addBook(book);
        return ResponseEntity.ok().build();
    }

    //Get Book
    @GetMapping("/book/{guid}")
    public ResponseEntity<?> getBookByGUID(@PathVariable String guid) throws IOException, ParseException {
        Book book = bookService.searchByGUID(guid);
        if (book==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    //Remove book
    @DeleteMapping("/book/{guid}")
    public ResponseEntity<?> removeBook(@PathVariable String guid) throws IOException, ParseException {
        Book book = bookService.searchByGUID(guid);
        if (book==null) {
            return ResponseEntity.notFound().build();
        }
        bookService.removeBook(guid);
        return ResponseEntity.ok().build();
    }

   //Order Book
    @PostMapping("/book/{guid}")
    public ResponseEntity<?> orderBook(@PathVariable String guid,
                                       @RequestParam("client") String client,
                                       @RequestParam("period") int days) throws IOException, ParseException {
        Book book = bookService.searchByGUID(guid);

        if (book==null || !book.getAvailb()) {
            return ResponseEntity.notFound().build();
        }

        if (days>60) {
            return ResponseEntity.badRequest().body("Book can be taken up to two months");
        }

        List<Order> orders = clientService.getOrderByClient(client);
        if (orders.size()>2) {
            return ResponseEntity.badRequest().body("No more then 3 books at a time");
        }

        bookService.setAvailability(false, guid);
        clientService.orderBook(new Order(client, utils.dateToday(), utils.dueDate(days), guid));

        return ResponseEntity.ok().build();
    }

    //Search book
    @GetMapping("/books/filter")
    public ResponseEntity<?> filterBooks(@RequestParam(value = "author", required = false) String author,
                                     @RequestParam(value = "category", required = false) String category,
                                     @RequestParam(value = "language", required = false) String lang,
                                     @RequestParam(value = "isbn", required = false) String isbn,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "availability", required = false) AvailabilityStatus status) throws IOException, ParseException {

        //TODO validation and exceptions
        List<Book> bookList = bookService.getAllBooks();
        if (author!=null) {
            bookList = bookService.searchListBy("Author", author, bookList);
        }
        if (category!=null) {
            bookList = bookService.searchListBy("Category", category, bookList);
        }
        if (lang!=null) {
            bookList = bookService.searchListBy("Language", lang, bookList);
        }
        if (isbn!=null) {
            bookList = bookService.searchListBy("ISBN", isbn, bookList);
        }
        if (name!=null) {
            bookList = bookService.searchListBy("Name", name, bookList);
        }
        if (status!=null) {
            bookList = bookService.searchListBy("Availability", status.name(), bookList);
        }

        if (bookList.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(bookList);
    }
}
