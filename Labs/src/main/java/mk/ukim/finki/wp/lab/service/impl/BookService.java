package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookStore;
import mk.ukim.finki.wp.lab.model.Review;
import mk.ukim.finki.wp.lab.repository.jpa.AuthorRepository;
import mk.ukim.finki.wp.lab.repository.jpa.BookRepository;
import mk.ukim.finki.wp.lab.repository.jpa.BookStoreRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ReviewRepository;
import mk.ukim.finki.wp.lab.service.AuthorServiceInterface;
import mk.ukim.finki.wp.lab.service.BookServiceInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements BookServiceInterface, AuthorServiceInterface
{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookStoreRepository bookStoreRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       BookStoreRepository bookStoreRepository)
    {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookStoreRepository = bookStoreRepository;
    }

    @Override
    public List<Book> listBooks()
    {
        return this.bookRepository.findAll();
    }

    @Override
    public Author addAuthorToBook(Long authorId, String isbn)
    {
        Author author = this.authorRepository.findById(authorId).orElse(null);
        if (author == null)
        {
            System.out.printf("No author with id: %d\n", authorId);
            return null;
        }
        Book book = this.bookRepository.findBookByIsbn(isbn);
        if (book == null)
        {
            System.out.printf("No book with isbn: %s\n", isbn);
            return null;
        }
        book.getAuthors().add(author);
        this.bookRepository.save(book);
        return author;
    }

    @Override
    public Book findBookByIsbn(String isbn)
    {
        return this.bookRepository.findBookByIsbn(isbn);
    }

    @Override
    public List<Book> findBooksByTitle(String text)
    {
        List<Book> allBooks = this.bookRepository.findAll();
        List<Book> result = new ArrayList<Book>();
        for (Book b : allBooks)
            if (b.getTitle().toLowerCase().contains(text.toLowerCase()))
                result.add(b);
        return result;
    }

    @Override
    public Book findBookByID(Long id)
    {
        return this.bookRepository.findBookById(id);
    }

    @Override
    public boolean deleteBookByID(Long id)
    {
        this.bookRepository.deleteById(id);
        return true;
    }

    @Override
    public void editBook(String id, String title, String isbn, String genre, String year, String storeID)
    {
        BookStore bs = this.bookStoreRepository.findById(Long.parseLong(storeID)).orElse(null);
        if (bs == null)
            return;
        Book bookToEdit = this.bookRepository.findBookById(Long.parseLong(id));
        bookToEdit.setTitle(title);
        bookToEdit.setIsbn(isbn);
        bookToEdit.setGenre(genre);
        bookToEdit.setYear(Integer.parseInt(year));
        bookToEdit.setBookStore(bs);
        this.bookRepository.save(bookToEdit);
    }

    @Override
    public void addNewBook(String isbn, String title, String genre, String year, String idStore)
    {
        if (title.equals("") || isbn.equals("") || genre.equals("") || year.equals(""))
            return;
        Book newBook = new Book();
        newBook.setIsbn(isbn);
        newBook.setTitle(title);
        newBook.setGenre(genre);
        newBook.setYear(Integer.parseInt(year));
        BookStore bs = this.bookStoreRepository.findById(Long.parseLong(idStore)).orElse(null);
        if (bs == null)
            return;
        newBook.setBookStore(bs);
        bookRepository.save(newBook);
    }


    @Override
    public List<Author> listAuthors()
    {
        return this.authorRepository.findAll();
    }

    @Override
    public Author findById(Long id)
    {
        return this.authorRepository.findById(id).orElse(null);
    }
}
