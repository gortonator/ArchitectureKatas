package book.demo.java.service;

import book.demo.java.entity.cart.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> getCartItemsByReaderId(int readerId);

    List<CartItem> getCartItemsByReaderUsername(String username);

    CartItem addCartItem(int bookVariantId, int quantity, String username);

    void removeByReaderUsernameAndBookVariantId(String username, int bookVariantId);

}
