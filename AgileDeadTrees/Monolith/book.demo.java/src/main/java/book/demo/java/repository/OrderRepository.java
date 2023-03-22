package book.demo.java.repository;

import book.demo.java.model.Order;
import book.demo.java.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByReaderId(int readerId);

    List<Order> findByReader(Reader reader);
}
