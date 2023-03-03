package book.demo.java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.Year;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
