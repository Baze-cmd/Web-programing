package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;

import java.util.List;

public interface BookServiceInterface
{
    List<Book> listBooks();

    Author addAuthorToBook(Long authorId, String isbn);

    Book findBookByIsbn(String isbn);
}