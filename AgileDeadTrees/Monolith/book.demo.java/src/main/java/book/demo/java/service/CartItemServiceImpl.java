package book.demo.java.service;

import book.demo.java.model.Book;
import book.demo.java.model.CartItem;
import book.demo.java.model.Reader;
import book.demo.java.repository.BookRepository;
import book.demo.java.repository.CartItemRepository;
import book.demo.java.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private ReaderRepository readerRepo;

    public List<CartItem> getCartItems(Reader reader) {
        return cartRepo.findByReader(reader);
    }

    public List<CartItem> getCartItemsByReaderId(int readerId) {
        return cartRepo.findByReaderId(readerId);
    }


    public CartItem addCartItem(int bookId, int quantity, int readerId) {
        CartItem cartItem = cartRepo.findByReaderIdAndBookId(readerId, bookId);

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        } else {
            Optional<Reader> reader = readerRepo.findById(readerId);
            Optional<Book> book = bookRepo .findById(bookId);

            cartItem = new CartItem(reader.get(), book.get(), quantity);
        }

        return cartRepo.save(cartItem);
    }

    public void removeByReaderIdAndBookId(int readerId, int bookId) {
        cartRepo.deleteByReaderIdAndBookId(readerId, bookId);
    }

    public void clearCartItemByReaderId(int readerId) {
        cartRepo.deleteByReaderId(readerId);
    }

}
