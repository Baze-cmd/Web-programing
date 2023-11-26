package mk.ukim.finki.wp.lab.model;
import lombok.Data;

@Data
public class BookStore
{
    public static Long generatorID = 0L;
    private Long id;
    private String name;
    private String city;
    private String address;

    public BookStore(String name, String city, String address)
    {
        this.id = generatorID++;
        this.name = name;
        this.city = city;
        this.address = address;
    }
}
