package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryBookRepository
{
    private final List<Book> books;

    public InMemoryBookRepository()
    {
        this.books = new ArrayList<Book>();
        IntStream.range(1, 6).forEach
                (
                        i ->
                        {
                            Author a = new Author((long)i,
                                    String.format("author%d", (int) (Math.random() * 100)),
                                    String.format("authorSurname%d", (int) (Math.random() * 100)),
                                    String.format("biography%d", (int) (Math.random() * 100)));
                            List<Author> aList = new ArrayList<Author>();
                            aList.add(a);
                            this.books.add
                                    (
                                            new Book(String.format("isbn%d", i), String.format("Book%d", i),
                                                    String.format("genre%d", i), 1769 + (i - 1) * 50,
                                                    aList)
                                    );
                        }
                );
    }

    public List<Book> findAll()
    {
        return this.books;
    }

    public Book findByIsbn(String isbn)
    {
        return this.books.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst().orElse(null);
    }

    public Author addAuthorToBook(Author author, Book book)
    {
        this.books.forEach
                (
                        b ->
                        {
                            if (b.getIsbn().equals(book.getIsbn()))
                                b.getAuthors().add(author);
                        }
                );
        return author;
    }
}
