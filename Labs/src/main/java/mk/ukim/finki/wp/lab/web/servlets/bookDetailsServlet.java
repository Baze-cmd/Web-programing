package mk.ukim.finki.wp.lab.web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.service.impl.BookService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "bookDetails", urlPatterns = "/servlet/book")
public class bookDetailsServlet extends HttpServlet
{
    private final SpringTemplateEngine springTemplateEngine;
    private final BookService bookService;

    public bookDetailsServlet(SpringTemplateEngine springTemplateEngine, BookService bookService)
    {
        this.springTemplateEngine = springTemplateEngine;
        this.bookService = bookService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(req.getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        String s = req.getParameter("author");
        if(s==null)
        {
            springTemplateEngine.process("noAuthorSelected.html",context,resp.getWriter());
            return;
        }
        Long authorId = Long.parseLong(s);
        String isbnBook = req.getParameter("bookIsbn");
        Author author = this.bookService.addAuthorToBook(authorId,isbnBook);
        if(author==null)
            context.setVariable("alreadyIn","Selected Author is already an author to the book");
        else
            context.setVariable("alreadyIn","");
        context.setVariable("book",this.bookService.findBookByIsbn(isbnBook));
        springTemplateEngine.process("bookDetails.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doGet(req,resp);
    }
}
