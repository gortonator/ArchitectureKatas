package book.demo.java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

import java.math.BigDecimal;

@Entity
public class CartLineItem {

    @EmbeddedId
    @JsonIgnore
    private CartLineItemPK pk;

    @Column(nullable = false)
    private Integer quantity;

    protected CartLineItem() { }

    public CartLineItem(Cart cart, Book book, Integer quantity) {
        this.pk = new CartLineItemPK(cart, book);
        this.quantity = quantity;
    }

    public CartLineItem(Cart cart, Book book) {
        this.pk = new CartLineItemPK(cart, book);
        this.quantity = 1;
    }

    @Transient
    public Book getBook() { return this.pk.getBook(); }

    @Transient
    public BigDecimal getAmount() {
        return getBook().getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }


    // attention: need hashCode() and Equals() or not?


    public CartLineItemPK getPk() {
        return pk;
    }

    public void setPk(CartLineItemPK pk) {
        this.pk = pk;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
