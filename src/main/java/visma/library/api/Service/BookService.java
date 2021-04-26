package visma.library.api.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import visma.library.api.Config.AvailabilityStatus;
import visma.library.api.Model.Book;
import visma.library.api.Repository.BookRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    //Add book to library
    public void addBook(Book book) throws IOException, ParseException {
        List<Book> library =  bookRepo.getLibrary();
        library.add(book);
        bookRepo.saveToFile(library);
    }

    //Get book from library
    public Book searchByGUID(String guid) throws IOException, ParseException {

        List<Book> library =  bookRepo.getLibrary();

        if (!library.isEmpty()) {
            Book book = streamGuidSearch(guid, library);
            return book;

        } else {
            return null;

        }
    }

    public void setAvailability(boolean status, String guid) throws IOException, ParseException {
        List<Book> library =  bookRepo.getLibrary();
        Book book = streamGuidSearch(guid, library);
        if (book!=null) {
            int index = library.indexOf(book);
            book.setAvailb(status);
            library.set(index, book);
            bookRepo.saveToFile(library);
        }
    }

    public void removeBook(String guid) throws IOException, ParseException {
        List<Book> library =  bookRepo.getLibrary();
        Book book = streamGuidSearch(guid, library);
        if (book!=null) {
            library.remove(book);
            bookRepo.saveToFile(library);
        }
    }

    private Book streamGuidSearch(String guid, List<Book> library) {
        return library.stream()
                .filter(x -> guid.equals(x.getGuid()))
                .findAny().orElse(null);
    }

    public List<Book> getAllBooks() throws IOException, ParseException {
        return bookRepo.getLibrary();
    }

    public List<Book> searchListBy(String field, String search, List<Book> list) {
        switch(field) {
            case "Name":
                return list.stream().filter(x -> x.getName().contains(search)).collect(Collectors.toList());
            case "Author":
                return list.stream().filter(x -> x.getAuthor().contains(search)).collect(Collectors.toList());
            case "Category":
                return list.stream().filter(x -> x.getCategory().contains(search)).collect(Collectors.toList());
            case "Language":
                return list.stream().filter(x -> x.getLanguage().contains(search)).collect(Collectors.toList());
            case "ISBN":
                return list.stream().filter(x -> x.getIsbn().contains(search)).collect(Collectors.toList());
            case "Availability":
                if (search.equals(AvailabilityStatus.available.name()))
                    return list.stream().filter(Book::getAvailb).collect(Collectors.toList());
                else if (search.equals(AvailabilityStatus.taken.name()))
                    return list.stream().filter(x -> !x.getAvailb()).collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
    }
}
