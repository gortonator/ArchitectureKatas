package book.demo.java.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @JsonManagedReference
    @OneToMany(mappedBy = "pk.order")
    private List<OrderLineItem> orderLineItemList = new ArrayList<>();

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
        List<OrderLineItem> orderLineItems = getOrderLineItemList();
        for (OrderLineItem item: orderLineItems) {
            amount = amount.add(item.getAmount());
        }
        return amount;
    }

    @Transient
    public int getNumberOfItems() {
        return this.orderLineItemList.size();
    }


    // Getters and Setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderLineItem> getOrderLineItemList() {
        return orderLineItemList;
    }

    public void setOrderLineItemList(List<OrderLineItem> orderLineItemList) {
        this.orderLineItemList = orderLineItemList;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
}
