package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.Review;
import mk.ukim.finki.wp.lab.service.ReviewServiceInterface;
import mk.ukim.finki.wp.lab.service.impl.BookService;
import mk.ukim.finki.wp.lab.service.impl.BookStoreService;
import mk.ukim.finki.wp.lab.service.impl.ReviewService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController
{
    private final BookService bookService;
    private final BookStoreService bookStoreService;
    private final ReviewService reviewService;

    public BooksController(BookService bookService,
                           BookStoreService bookStoreService,
                           ReviewService reviewService)
    {
        this.bookService = bookService;
        this.bookStoreService = bookStoreService;
        this.reviewService = reviewService;
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
        if (title.equals("") || isbn.equals("") || genre.equals("") || year.equals(""))
            return "redirect:/books";
        this.bookService.editBook(id, title, isbn, genre, year, storeID);
        return "redirect:/books";
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

    //filter po keyword kaj reviews
    @PostMapping("/filterByKeyword")
    public String filterByKeywordForBook(@RequestParam(name = "bookID") String id,
                                         @RequestParam(name = "keyword") String keyword,
                                         Model model)
    {
        Book book = this.bookService.findBookByID(Long.parseLong(id));
        List<Review> reviews = this.reviewService.findReviewsForBookWithKeyword(Long.parseLong(id),keyword);
        double averageRating = reviews.stream().mapToDouble(Review::getScore).sum();
        model.addAttribute("book", book);
        model.addAttribute("averageRating",
                String.format("%.2f", averageRating / reviews.size()));
        model.addAttribute("reviews", reviews);
        return "book-reviews";
    }

    @GetMapping("/reviews/{id}")
    public String getReviewPage(@PathVariable Long id, Model model)
    {
        Book book = this.bookService.findBookByID(id);
        List<Review> reviews = this.reviewService.getReviewsForBook(id);
        double averageRating = reviews.stream().mapToDouble(Review::getScore).sum();
        model.addAttribute("book", book);
        model.addAttribute("averageRating",
                String.format("%.2f", averageRating / reviews.size()));
        model.addAttribute("reviews", reviews);
        return "book-reviews";
    }

    @PostMapping("/reviews/addReview")
    public String addReviewToBook(@RequestParam(name = "bookId") String id,
                                  @RequestParam(name = "score") String score,
                                  @RequestParam(name = "description") String description)
    {
        Book book = this.bookService.findBookByID(Long.parseLong(id));
        if (book == null)
            return "redirect:/books/reviews/" + id;
        Review review = new Review(Integer.parseInt(score), description, book, LocalDateTime.now());
        this.reviewService.saveReview(review);

        return "redirect:/books/reviews/" + id;
    }

    @PostMapping("/reviews/filterFromTo")
    public String filterReviews(
            @RequestParam(name = "bookID") String id,
            @RequestParam(name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime from,
            @RequestParam(name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime to,
            Model model)
    {
        Book book = this.bookService.findBookByID(Long.parseLong(id));
        List<Review> reviews = this.reviewService.getReviewsWithinTimeIntervalForBook(Long.parseLong(id), from, to);
        double averageRating = reviews.stream().mapToDouble(Review::getScore).sum();
        model.addAttribute("book", book);
        model.addAttribute("averageRating",
                String.format("%.2f", averageRating / reviews.size()));
        model.addAttribute("reviews", reviews);
        return "book-reviews";
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
        this.bookService.addNewBook(isbn, title, genre, year, idStore);
        return "redirect:/books";
    }
}
