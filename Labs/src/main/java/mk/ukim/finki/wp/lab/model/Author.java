package mk.ukim.finki.wp.lab.model;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String biography;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    public Author()
    {
    }
    public Author(String name, String surname, String biography,LocalDate dateOfBirth)
    {
        this.name = name;
        this.surname = surname;
        this.biography = biography;
        this.dateOfBirth = dateOfBirth;
    }
    public Author(Long id, String name, String surname, String biography)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.biography = biography;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Author)) return false;
        Author other = (Author) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, surname, biography, dateOfBirth);
    }
}
