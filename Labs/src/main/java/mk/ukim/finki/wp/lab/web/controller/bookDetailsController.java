package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.service.impl.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/book")
public class bookDetailsController
{
    private final BookService bookService;

    public bookDetailsController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @GetMapping
    public String getBookDetailsPage(@RequestParam(name = "author") String authorID,
                                     @RequestParam(name = "bookIsbn") String bookIsbn, Model model)
    {
        if (authorID == null)
            return "noAuthorSelected";
        Long authorId = Long.parseLong(authorID);
        Author author = this.bookService.addAuthorToBook(authorId, bookIsbn);
        if (author == null)
            model.addAttribute("alreadyIn", "Selected Author is already an author to the book");
        else
            model.addAttribute("alreadyIn", "");
        model.addAttribute("book", this.bookService.findBookByIsbn(bookIsbn));
        return "bookDetails";
    }
}
