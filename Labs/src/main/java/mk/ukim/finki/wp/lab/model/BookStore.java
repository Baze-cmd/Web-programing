package mk.ukim.finki.wp.lab.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BookStore
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String address;

    public BookStore()
    {
    }
    public BookStore(String name, String city, String address)
    {
        this.name = name;
        this.city = city;
        this.address = address;
    }
    public BookStore(Long id,String name, String city, String address)
    {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
    }

}
