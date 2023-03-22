package book.demo.java.service;

import book.demo.java.model.CartItem;

import java.util.List;

public interface CartItemService {

//    List<CartItem> getCartItems(Reader reader);

    List<CartItem> getCartItemsByReaderId(int readerId);

//    CartItem addCartItem(Book book, int quantity, Reader reader);
    CartItem addCartItem(int bookId, int quantity, int readerId);

    void removeByReaderIdAndBookId(int readerId, int bookId);

    void clearCartItemByReaderId(int readerId);
}
