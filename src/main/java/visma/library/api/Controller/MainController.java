package visma.library.api.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import visma.library.api.Config.AvailabilityStatus;
import visma.library.api.Model.Book;
import visma.library.api.Model.Client;
import visma.library.api.Service.BookService;
import visma.library.api.Service.ClientService;
import visma.library.api.Utils.Utils;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/library")
public class MainController {

    @Autowired
    BookService bookService;
    @Autowired
    ClientService clientService;
    private Utils utils;

    @PostMapping("/book")
    public ResponseEntity addBook(@RequestBody Book book)  {
        //TODO validation and exceptions
        //bookService.addBook(book);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/book/{guid}")
    public ResponseEntity getBookByGUID(@PathVariable String guid)  {
        //TODO validation and exceptions
        JSONObject book = bookService.searchBy("GUID", guid);
        //bookService.addBook(book);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/book/{guid}")
    public ResponseEntity orderBook(@PathVariable String guid,
                                    @RequestParam("client") String client,
                                    @RequestParam("period") int days)  {
        //TODO check is book available
        //TODO check books taken by client
        //TODO check period no more then 2 months
        bookService.setUnavailable(guid);
        clientService.orderBook(new Client(client, utils.dateToday(), utils.dueDate(days), guid));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/book/{guid}")
    public ResponseEntity removeBook(@PathVariable String guid)  {
        //TODO validation and exceptions
        bookService.removeBook(guid);
        //bookService.addBook(book);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/books")
    public ResponseEntity searchBook(@RequestParam(value = "author", required = false) String author,
                                     @RequestParam(value = "category", required = false) String category,
                                     @RequestParam(value = "language", required = false) String lang,
                                     @RequestParam(value = "isbn", required = false) String isbn,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "status", required = false) AvailabilityStatus status)  {

        //TODO validation and exceptions
        JSONArray bookList = bookService.getAllBooks();
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
        if (status==AvailabilityStatus.available) {
            bookList = bookService.searchListBy("Availability", status.name(), bookList);
        }
        return ResponseEntity.ok(bookList);
    }
}
