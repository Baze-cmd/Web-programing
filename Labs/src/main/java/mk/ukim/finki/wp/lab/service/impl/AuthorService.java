package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.repository.jpa.AuthorRepository;
import mk.ukim.finki.wp.lab.service.AuthorServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements AuthorServiceInterface
{
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository)
    {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> listAuthors()
    {
        return this.authorRepository.findAll();
    }

    @Override
    public Author findById(Long id)
    {
        return authorRepository.findById(id).orElse(null);
    }
}
