package book.demo.java.repository;

import book.demo.java.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByReaderId(int readerId);

    List<CartItem> findByReaderUsername(String username);

    CartItem findByReaderUsernameAndBookVariantId(String username, int bookVariantId);

    void deleteByReaderUsernameAndBookVariantId(String username, int bookVariantId);

}
