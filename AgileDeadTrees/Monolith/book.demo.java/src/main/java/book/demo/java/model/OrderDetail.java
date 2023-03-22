package book.demo.java.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    public OrderDetail() {}

    protected OrderDetail(int id) {
        this.id = id;
    }

    public OrderDetail(Book book, Order order, int quantity) {
        this.book = book;
        this.order = order;
        this.quantity = quantity;
    }

    @Transient
    public BigDecimal getSubtotal() {
        return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
