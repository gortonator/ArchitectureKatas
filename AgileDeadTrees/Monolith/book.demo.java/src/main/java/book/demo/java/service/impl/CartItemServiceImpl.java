package book.demo.java.service.impl;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.book.published.BookVariant;
import book.demo.java.entity.cart.CartItem;
import book.demo.java.repository.BookVariantRepository;
import book.demo.java.repository.CartItemRepository;
import book.demo.java.repository.ReaderRepository;
import book.demo.java.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private BookVariantRepository bookVariantRepo;

    @Autowired
    private ReaderRepository readerRepo;

    public List<CartItem> getCartItemsByReaderId(int readerId) {
        return cartRepo.findByReaderId(readerId);
    }

    @Override
    public List<CartItem> getCartItemsByReaderUsername(String username) {
        return cartRepo.findByReaderUsername(username);
    }


    public CartItem addCartItem(int bookVariantId, int quantity, String username) {
        CartItem cartItem = cartRepo.findByReaderIdAndBookVariantId(username, bookVariantId);

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        } else {
            Reader reader = readerRepo.findByUsername(username);
            if (reader == null) {
                throw new NoSuchElementException("Reader username " + username + " NOT FOUND.");
            }
            BookVariant bookVariant = bookVariantRepo.findById(bookVariantId).orElseThrow(() ->
                    new NoSuchElementException("Book variant id " + bookVariantId + " NOT FOUND."));
            cartItem = new CartItem(reader, bookVariant, quantity);
        }
        return cartRepo.save(cartItem);
    }


    public void removeByReaderIdAndBookVariantId(String username, int bookVariantId) {
        cartRepo.deleteByReaderIdAndBookVariantId(username, bookVariantId);
    }


}
