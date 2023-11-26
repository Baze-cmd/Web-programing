package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.BookStore;
import mk.ukim.finki.wp.lab.repository.BookStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookStoreService implements BookStoreServiceInterface
{
    private final BookStoreRepository bookStoreRepository;

    public BookStoreService(BookStoreRepository bookStoreRepository)
    {
        this.bookStoreRepository = bookStoreRepository;
    }

    @Override
    public List<BookStore> findAll()
    {
        return bookStoreRepository.findAll();
    }
}
