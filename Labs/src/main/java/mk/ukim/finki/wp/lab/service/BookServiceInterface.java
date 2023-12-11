package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.Review;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookServiceInterface
{
    List<Book> listBooks();

    Author addAuthorToBook(Long authorId, String isbn);

    Book findBookByIsbn(String isbn);
    List<Book> findBooksByTitle(String text);
    Book findBookByID(Long id);
    boolean deleteBookByID(Long id);
    void editBook(String id, String title, String isbn, String genre, String year, String storeID);
    void addNewBook(String isbn, String title, String genre, String year, String idStore);
}