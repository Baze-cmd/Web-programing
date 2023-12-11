package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.model.BookStore;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Repository
public class InMemoryBookStoreRepository
{
    private final List<BookStore> bookStores;

    public InMemoryBookStoreRepository()
    {
        bookStores = new ArrayList<BookStore>();
        Random random = new Random();
        IntStream.range(1, 6).forEach
                (
                        i ->
                        {
                            BookStore store = new BookStore
                                    (
                                            String.format("Store%c%d",
                                                    (char) (random.nextInt(26) + 'A'),
                                                    i),
                                            String.format("City%c%d",
                                                    (char) (random.nextInt(26) + 'A'),
                                                    i),
                                            String.format("Address #%d", random.nextInt(99) + i)
                                    );
                            this.bookStores.add(store);
                        }
                );
    }

    public List<BookStore> findAll()
    {
        return this.bookStores;
    }
}
