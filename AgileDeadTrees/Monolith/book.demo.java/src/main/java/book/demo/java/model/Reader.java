package book.demo.java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "readers")
@Data
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private int id;

    @Size(min = 2)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(min = 2)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(min = 5)
    @Column(nullable = false, unique = true)
    private String username;

    private String address;

    @OneToMany(mappedBy = "reader")
    private List<Order> orderList = new ArrayList<>();

    protected Reader() {
    }

    public Reader(String firstName, String lastName, String username, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.address = address;
    }

}
