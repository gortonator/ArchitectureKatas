package book.demo.java.service;

import book.demo.java.model.Book;
import book.demo.java.model.CartItem;
import book.demo.java.model.Reader;

import java.util.List;

public interface CartItemService {

    List<CartItem> getCartItems(Reader reader);

    List<CartItem> getCartItemsByReaderId(int readerId);

    CartItem addCartItem(Book book, int quantity, Reader reader);

    void removeCartItem(Book book, Reader reader);

    void clearCartItemByReader(Reader reader);
}
