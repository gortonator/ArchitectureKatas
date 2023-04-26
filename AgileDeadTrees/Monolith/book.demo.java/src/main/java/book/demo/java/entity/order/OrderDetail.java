package book.demo.java.entity.order;

import book.demo.java.entity.book.published.BookVariant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = -1004385365835343408L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_variant_id")
    @JsonBackReference
    private BookVariant bookVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @Column(nullable = false)
    private int quantity;

    public OrderDetail() {
    }

    protected OrderDetail(int id) {
        this.id = id;
    }

    public OrderDetail(BookVariant bookVariant, Order order, int quantity) {
        this.bookVariant = bookVariant;
        this.order = order;
        this.quantity = quantity;
    }

    @Transient
    public BigDecimal getSubtotal() {
        return bookVariant.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Transient
    public int getBookVariantId() {
        return bookVariant.getId();
    }
}
