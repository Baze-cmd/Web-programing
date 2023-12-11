package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.wp.lab.repository.impl.InMemoryBookStoreRepository;

import java.util.List;
import java.util.Random;

@Data
@Entity
public class Book
{
    private static Long generatorID = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BookStore bookStore;
    private String isbn;
    private String title;
    private String genre;
    private int year;
    @ManyToMany
    private List<Author> authors;

    public Book()
    {

    }

    public Book(String isbn, String title, String genre, int year, List<Author> authors)
    {
        this.id = generatorID++;
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.authors = authors;
        InMemoryBookStoreRepository repository = new InMemoryBookStoreRepository();
        List<BookStore> stores = repository.findAll();
        Random r = new Random();
        this.bookStore = stores.get(r.nextInt(stores.size()));
    }


}
