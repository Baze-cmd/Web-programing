package mk.ukim.finki.wp.lab.model;
import lombok.Data;
import mk.ukim.finki.wp.lab.repository.BookStoreRepository;

import java.util.List;
import java.util.Random;

@Data
public class Book
{
    private static Long generatorID = 0L;
    private Long id;
    private BookStore bookStore;
    private String isbn;
    private String title;
    private String genre;
    private int year;
    private List<Author> authors;
    public Book(String isbn, String title, String genre, int year, List<Author> authors)
    {
        this.id = generatorID++;
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.authors = authors;
        BookStoreRepository repository = new BookStoreRepository();
        List<BookStore> stores = repository.findAll();
        Random r = new Random();
        this.bookStore = stores.get(r.nextInt(stores.size()));
    }
}
