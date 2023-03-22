package book.demo.java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.Year;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Size(min=1)
    private String author;

    @Size(min=1)
    private String title;

    @Column(length = 13)
    private String ISBN;

    @Temporal(TemporalType.DATE)
    private Year year;

    @Size(min=1)
    private BigDecimal price;

    @ConstructorProperties({"book_id", "author", "ISBN", "year", "price"})
    protected Book() {}

    public Book(String author, String title, String ISBN, Year year, BigDecimal price) {
        this.author = author;
        this.title = title;
        this.ISBN = ISBN;
        this.year = year;
        this.price = price;
    }

}
