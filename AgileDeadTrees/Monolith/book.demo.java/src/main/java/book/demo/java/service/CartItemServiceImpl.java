package book.demo.java.service;

import book.demo.java.model.Book;
import book.demo.java.model.CartItem;
import book.demo.java.model.Reader;
import book.demo.java.repository.BookRepository;
import book.demo.java.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private BookRepository bookRepo;

    public List<CartItem> getCartItems(Reader reader) {
        return cartRepo.findByReader(reader);
    }

    public List<CartItem> getCartItemsByReaderId(int readerId) {
        return cartRepo.findByReaderId(readerId);
    }


    // attention: using Book or Book id
    public CartItem addCartItem(Book book, int quantity, Reader reader) {
        CartItem cartItem = cartRepo.findByReaderAndBook(reader, book);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem(reader, book, quantity);
        }

        return cartRepo.save(cartItem);
    }

    public void removeCartItem(Book book, Reader reader) {
        cartRepo.deleteByReaderAndBook(reader, book);
    }

    public void clearCartItemByReader(Reader reader) {
        cartRepo.deleteByReader(reader);
    }

}
