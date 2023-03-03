package book.demo.java.service;

import book.demo.java.model.Cart;

public interface CartService {

    Cart getCartByReaderId(int readerId);
}
