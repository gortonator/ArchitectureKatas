package book.demo.java.repository;

import book.demo.java.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByReaderReaderId(int readerId);
}
