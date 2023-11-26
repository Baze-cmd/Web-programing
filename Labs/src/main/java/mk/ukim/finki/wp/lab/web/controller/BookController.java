package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookStore;
import mk.ukim.finki.wp.lab.service.BookService;
import mk.ukim.finki.wp.lab.service.BookStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController
{
    private final BookService bookService;
    private final BookStoreService bookStoreService;

    public BookController(BookService bookService, BookStoreService bookStoreService)
    {
        this.bookService = bookService;
        this.bookStoreService = bookStoreService;
    }

    @GetMapping
    public String getBooksPage(@RequestParam(required = false) String error, Model model)
    {
        if (error != null && !error.isEmpty())
        {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("books", this.bookService.listBooks());
        return "listBooks";
    }

    @PostMapping
    public String searchResults(@RequestParam(name = "bookToSearch") String bookToSearch, Model model)
    {
        if (bookToSearch == null) return "redirect:/books";
        List<Book> booksToList = new ArrayList<>();
        List<Book> allBooks = this.bookService.listBooks();
        for (Book b : allBooks)
            if (b.getTitle().toLowerCase().contains(bookToSearch.toLowerCase())) booksToList.add(b);
        model.addAttribute("books", booksToList);
        return "listBooks";
    }

    @PostMapping("/editBook")
    public String handleEditBookForm(@RequestParam(name = "id") String id,
                                     @RequestParam(name = "editTitle") String title,
                                     @RequestParam(name = "editIsbn") String isbn,
                                     @RequestParam(name = "editGenre") String genre,
                                     @RequestParam(name = "editYear") String year,
                                     @RequestParam(name = "editStore") String storeID)
    {
        if (title.equals("") || isbn.equals("") || genre.equals("") || year.equals(""))
            return "redirect:/books";
        int y = Integer.parseInt(year);
        int ID = Integer.parseInt(id);
        List<Book> books = this.bookService.listBooks();
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
        BookStore bs = null;
        this.bookService.listBooks().get(index).setTitle(title);
        this.bookService.listBooks().get(index).setIsbn(isbn);
        this.bookService.listBooks().get(index).setGenre(genre);
        this.bookService.listBooks().get(index).setYear(y);
        this.bookService.listBooks().get(index).setBookStore(store);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookPage(@PathVariable Long id, Model model)
    {
        List<Book> books = this.bookService.listBooks();
        Book bookToEdit = null;
        for (Book b : books)
        {
            if (b.getId().equals(id))
            {
                bookToEdit = b;
                break;
            }
        }
        if (bookToEdit == null) return "redirect:/books?error=book Not Found";

        // Add the bookToEdit to the model to populate the edit form
        model.addAttribute("book", bookToEdit);
        model.addAttribute("stores", this.bookStoreService.findAll());
        return "editBook"; // Return the name of the edit book view (HTML template)
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Model model)
    {
        List<Book> books = this.bookService.listBooks();
        Book bookToDelete = null;
        for (Book b : books)
        {
            if (b.getId().equals(id))
            {
                bookToDelete = b;
                break;
            }
        }
        this.bookService.listBooks().remove(bookToDelete);
        return "redirect:/books";
    }

    @GetMapping("/add")
    public String addNewBookPage(Model model)
    {
        model.addAttribute("bookStores", this.bookStoreService.findAll());
        return "add-book";
    }

    @PostMapping("/addNewBook")
    public String saveBook(@RequestParam(name = "isbn") String isbn,
                           @RequestParam(name = "Title") String title,
                           @RequestParam(name = "genre") String genre,
                           @RequestParam(name = "year") String year,
                           @RequestParam(name = "store") String idStore)
    {
        if (title.equals("") || isbn.equals("") || genre.equals("") || year.equals(""))
            return "redirect:/books";
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
            return "redirect:/books";
        newBook.setBookStore(store);
        this.bookService.listBooks().add(newBook);
        return "redirect:/books";
    }
}
