package mk.ukim.finki.wp.lab.web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.impl.AuthorService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "AuthorServlet", urlPatterns = "/servlet/author")
public class AuthorServlet extends HttpServlet
{
    private final SpringTemplateEngine springTemplateEngine;
    private final AuthorService authorService;

    public AuthorServlet(SpringTemplateEngine springTemplateEngine, AuthorService authorService)
    {
        this.springTemplateEngine = springTemplateEngine;
        this.authorService = authorService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(req.getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        if(req.getParameter("book")==null)
        {
            springTemplateEngine.process("noBookSelected.html",context,resp.getWriter());
            return;
        }
        context.setVariable("authors", authorService.listAuthors());
        context.setVariable("bookIsbn", req.getParameter("book"));
        springTemplateEngine.process("authorList.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doGet(req,resp);
    }
}
