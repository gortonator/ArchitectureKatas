package book.demo.java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

import java.math.BigDecimal;

@Entity
public class OrderLineItem {

    @EmbeddedId
    @JsonIgnore
    private OrderLineItemPK pk;

    @Column(nullable = false)
    private Integer quantity;

    protected OrderLineItem() { }


    public OrderLineItem(Order order, Book book, Integer quantity) {
        this.pk = new OrderLineItemPK(order, book);
        this.quantity = quantity;
    }

    public OrderLineItem(Order order, Book book) {
        this.pk = new OrderLineItemPK(order, book);
        this.quantity = 1;
    }

    @Transient
    public Book getBook() { return this.pk.getBook(); }

    public BigDecimal getAmount() {
        return getBook().getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }


    // attention: need hashCode() and Equals() or not?

    public OrderLineItemPK getPk() {
        return pk;
    }

    public void setPk(OrderLineItemPK pk) {
        this.pk = pk;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
