package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Author;

import java.util.List;

public interface AuthorServiceInterface
{
    List<Author> listAuthors();

    Author findById(Long id);
}