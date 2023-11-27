package mk.ukim.finki.wp.lab.web.controller;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.service.BookService;
import mk.ukim.finki.wp.lab.service.BookStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String searchResults(@RequestParam(name = "bookToSearch") String title, Model model)
    {
        if (title == null)
            return "redirect:/books";
        List<Book> resultBooks = this.bookService.findBooksByTitle(title);
        model.addAttribute("books", resultBooks);
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
        return this.bookService.editBook(id,title,isbn,genre,year,storeID);
    }

    @GetMapping("/edit-form/{id}")
    public String getEditBookForm(@PathVariable Long id, Model model)
    {

        Book bookToEdit = this.bookService.findBookByID(id);
        if (bookToEdit == null)
            return "redirect:/books?error=book Not Found";
        model.addAttribute("book", bookToEdit);
        model.addAttribute("stores", this.bookStoreService.findAll());
        return "edit-book";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id)
    {
        this.bookService.deleteBookByID(id);
        return "redirect:/books";
    }

    @GetMapping("/add-form")
    public String getAddBookPage(Model model)
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
        this.bookService.addNewBook(isbn,title,genre,year,idStore);
        return "redirect:/books";
    }
}
