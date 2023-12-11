package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer score;
    private String description;
    @ManyToOne(cascade  = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Book book;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public Review()
    {
    }
    public Review(Integer score, String description, Book book, LocalDateTime timestamp)
    {
        this.score = score;
        this.description = description;
        this.book = book;
        this.timestamp = timestamp;
    }
    public Review(Long id, Integer score, String description, Book book, LocalDateTime timestamp)
    {
        this.id = id;
        this.score = score;
        this.description = description;
        this.book = book;
        this.timestamp = timestamp;
    }
}
