package book.demo.java.entity.cart;


import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.book.published.BookVariant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -913266209945776105L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private int id;

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    @JsonBackReference
    private Reader reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_variant_id")
    @JsonBackReference
    private BookVariant bookVariant;

    public CartItem() {
    }

    public CartItem(Reader reader, BookVariant bookVariant, int quantity) {
        this.reader = reader;
        this.bookVariant = bookVariant;
        this.quantity = quantity;
    }

    @Transient
    public BigDecimal getSubtotal() {
        return bookVariant.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Transient
    public int getIdOfReader() {
        return reader.getId();
    }

    @Transient
    public int getIdOfBookVariant() {
        return bookVariant.getId();
    }

}
