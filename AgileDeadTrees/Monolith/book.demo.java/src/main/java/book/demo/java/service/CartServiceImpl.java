package book.demo.java.service;

import book.demo.java.model.Cart;
import book.demo.java.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public Cart getCartByReaderId(int readerId) {
        //attention: Exception?
        return cartRepository.findByReaderReaderId(readerId);
    }
}
