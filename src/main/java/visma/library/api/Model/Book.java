package visma.library.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    @JsonProperty("Name")
    @Pattern(regexp="^[A-Za-z]*$",message = "Name field must contain just letters")
    @NotEmpty
    private String name;
    @JsonProperty("Author")
    @Pattern(regexp="^[A-Za-z]*$",message = "Author field must contain just letters")
    @NotEmpty
    private String author;
    @JsonProperty("Category")
    @Pattern(regexp="^[A-Za-z]*$",message = "Category field must contain just letters")
    @NotEmpty
    private String category;
    @JsonProperty("Language")
    @Pattern(regexp="^[A-Za-z]*$",message = "Language field must contain just letters")
    @NotEmpty
    private String language;
    @JsonProperty("Publication date")
    @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$",message = "Bad date format")
    @NotEmpty
    private String publDate;
    @JsonProperty("ISBN")
    @NotEmpty
    private String isbn;
    @JsonProperty("GUID")
    @NotEmpty
    private String guid;
    @JsonProperty("Availability")
    private boolean availb;

    public Book() {
    }

    public Book(String name, String author, String category, String language, String publDate, String isbn, String guid) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.language = language;
        this.publDate = publDate;
        this.isbn = isbn;
        this.guid = guid;
        this.availb = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublDate() {
        return publDate;
    }

    public void setPublDate(String publDate) {
        this.publDate = publDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean getAvailb() {
        return availb;
    }

    public void setAvailb(boolean availb) {
        this.availb = availb;
    }
}
