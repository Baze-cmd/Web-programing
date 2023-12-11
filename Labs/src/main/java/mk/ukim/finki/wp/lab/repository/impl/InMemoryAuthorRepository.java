package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.model.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class InMemoryAuthorRepository
{
    private final List<Author> authors;

    public InMemoryAuthorRepository()
    {
        this.authors = new ArrayList<>();
        IntStream.range(1, 6).forEach(i ->
        {
            this.authors.add
                    (
                            new Author((long)(Math.random()*100),
                                    String.format("author%d", i),
                                    String.format("authorSurname%d", i),
                                    String.format("biography%d", i))
                    );
        });
    }

    public Optional<Author> findById(Long id)
    {
        return this.authors.stream().filter(author -> author.getId() == id).findFirst();
    }

    public List<Author> findAll()
    {
        return this.authors;
    }

}
