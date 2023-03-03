package book.demo.java.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cartId;

    @JsonManagedReference
    @OneToMany(mappedBy = "pk.cart")
    private List<CartLineItem> cartLineItemList = new ArrayList<>();

    @OneToOne (mappedBy = "cart")
    private Reader reader;

    protected Cart() {}

    public Cart(Reader reader) {
        this.reader = reader;
    }

    @Transient
    public BigDecimal getTotalAmount() {
        BigDecimal amount = BigDecimal.valueOf(0);
        List<CartLineItem> cartLineItems = getCartLineItemList();
        for (CartLineItem item: cartLineItems) {
            amount = amount.add(item.getAmount());
        }
        return amount;
    }

    @Transient
    public int getNumberOfItems() {
        return this.cartLineItemList.size();
    }

    // Getters and Setters

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public List<CartLineItem> getCartLineItemList() {
        return cartLineItemList;
    }

    public void setCartLineItemList(List<CartLineItem> cartLineItemList) {
        this.cartLineItemList = cartLineItemList;
    }

    public Reader getReader() {
        return reader;
    }

    protected void setReader(Reader reader) {
        this.reader = reader;
    }
}
