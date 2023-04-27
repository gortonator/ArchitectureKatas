/**
 * This entity class represents a book variant stem from a published book entity. It is mainly used to
 * distinguish what book format the variant is in and its corresponding price for readers to place an order.
 *
 * @author Tong
 */

package book.demo.java.entity.book.published;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "book_variants")
@Data
public class BookVariant implements Serializable {

    @Serial
    private static final long serialVersionUID = -205857131052908095L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_variant_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "published_book_id")
    @JsonBackReference
    private PublishedBook book;

    @Column(length = 13)
    private String isbn;

    @Enumerated(EnumType.STRING)
    private BookFormat format;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    public BookVariant() {
    }

    public BookVariant(PublishedBook book, String isbn, BookFormat format, LocalDate publishDate, BigDecimal price) {
        this.book = book;
        this.isbn = isbn;
        this.format = format;
        this.publishDate = publishDate;
        this.price = price;
    }
}
