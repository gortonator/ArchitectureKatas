package book.demo.java.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated = LocalDate.now();

    protected Order() {}

    public Order(Reader reader) {
        this.reader = reader;
    }

    @Transient
    public BigDecimal getTotalAmount() {
        BigDecimal amount = BigDecimal.valueOf(0);
        List<OrderDetail> orderDetails = getOrderDetails();
        for (OrderDetail item: orderDetails) {
            amount = amount.add(item.getSubtotal());
        }
        return amount;
    }

    @Transient
    public int getNumberOfItems() {
        return this.orderDetails.size();
    }

}
