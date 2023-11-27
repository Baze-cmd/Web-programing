package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookStore;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements BookServiceInterface, AuthorServiceInterface
{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookStoreService bookStoreService;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookStoreService bookStoreService)
    {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookStoreService = bookStoreService;
    }

    @Override
    public List<Book> listBooks()
    {
        return bookRepository.findAll();
    }

    @Override
    public Author addAuthorToBook(Long authorId, String isbn)
    {

        Author author = this.authorRepository.findAll().stream().filter(a -> a.getId().equals(authorId)).findFirst().orElse(null);
        if (author == null)
        {
            System.out.printf("No author with id: %d\n", authorId);
            return null;
        }
        Book book = this.bookRepository.findAll().stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
        if (book == null)
        {
            System.out.printf("No book with isbn: %s\n", isbn);
            return null;
        }
        List<Author> authors = this.bookRepository.findByIsbn(isbn).getAuthors();
        for (Author a : authors)
            if (a.getId().equals(authorId))
                return null;
        this.bookRepository.addAuthorToBook(author, book);
        return author;
    }

    @Override
    public Book findBookByIsbn(String isbn)
    {
        return this.bookRepository.findByIsbn(isbn);
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
        List<Book> books = this.bookRepository.findAll();
        Book result = null;
        for (Book b : books)
        {
            if (b.getId().equals(id))
            {
                result = b;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean deleteBookByID(Long id)
    {
        List<Book> books = this.bookRepository.findAll();
        Book bookToDelete = null;
        for (Book b : books)
        {
            if (b.getId().equals(id))
            {
                bookToDelete = b;
                break;
            }
        }
        return this.bookRepository.findAll().remove(bookToDelete);
    }

    @Override
    public String editBook(String id, String title, String isbn, String genre, String year, String storeID)
    {
        if (title.equals("") || isbn.equals("") || genre.equals("") || year.equals(""))
            return "redirect:/books";
        int y = Integer.parseInt(year);
        int ID = Integer.parseInt(id);
        List<Book> books = this.bookRepository.findAll();
        int index = -1;
        for (int i = 0; i < books.size(); i++)
        {
            if (books.get(i).getId() == ID)
            {
                index = i;
                break;
            }
        }
        List<BookStore> stores = this.bookStoreService.findAll();
        BookStore store = null;
        for (BookStore bs : stores)
        {
            if (bs.getId() == Long.parseLong(storeID))
            {
                store = bs;
                break;
            }
        }
        this.bookRepository.findAll().get(index).setTitle(title);
        this.bookRepository.findAll().get(index).setIsbn(isbn);
        this.bookRepository.findAll().get(index).setGenre(genre);
        this.bookRepository.findAll().get(index).setYear(y);
        this.bookRepository.findAll().get(index).setBookStore(store);
        return "redirect:/books";
    }

    @Override
    public void addNewBook(String isbn, String title, String genre, String year, String idStore)
    {
        if (title.equals("") || isbn.equals("") || genre.equals("") || year.equals(""))
            return;
        Book newBook = new Book(isbn, title, genre, Integer.parseInt(year), new ArrayList<Author>());
        List<BookStore> stores = this.bookStoreService.findAll();
        BookStore store = null;
        for (BookStore bs : stores)
        {
            if (bs.getId().equals(Long.parseLong(idStore)))
            {
                store = bs;
                break;
            }
        }
        if (store == null)
            return;
        newBook.setBookStore(store);
        this.bookRepository.findAll().add(newBook);
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
