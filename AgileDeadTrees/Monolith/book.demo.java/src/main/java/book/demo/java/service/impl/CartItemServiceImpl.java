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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

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

        // If the book variant has been added to the reader's cart before, then update the quantity value
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        } else {
            // If cartItem is null (meaning the book variant has not been added to cart before),
            // then a new CartItem would be created.
            Reader reader = readerRepo.findByUsername(username);
            if (reader == null) {
                throw new EntityNotFoundException("Reader username " + username + " NOT FOUND.");
            }
            BookVariant bookVariant = bookVariantRepo.findById(bookVariantId).orElseThrow(() ->
                    new EntityNotFoundException("Book variant id " + bookVariantId + " NOT FOUND."));
            cartItem = new CartItem(reader, bookVariant, quantity);
        }
        return cartRepo.save(cartItem);
    }

    public void removeByReaderIdAndBookVariantId(String username, int bookVariantId) {
        cartRepo.deleteByReaderIdAndBookVariantId(username, bookVariantId);
    }
}
