package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements BookServiceInterface, AuthorServiceInterface
{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository)
    {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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
        if(author==null)
        {
            System.out.printf("No author with id: %d\n",authorId);
            return null;
        }
        Book book = this.bookRepository.findAll().stream().filter(b->b.getIsbn().equals(isbn)).findFirst().orElse(null);
        if(book == null)
        {
            System.out.printf("No book with isbn: %s\n",isbn);
            return null;
        }
        List<Author> authors = this.bookRepository.findByIsbn(isbn).getAuthors();
        for(Author a : authors)
            if(a.getId().equals(authorId))
                return null;
        this.bookRepository.addAuthorToBook(author,book);
        return author;
    }

    @Override
    public Book findBookByIsbn(String isbn)
    {
        return this.bookRepository.findByIsbn(isbn);
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
