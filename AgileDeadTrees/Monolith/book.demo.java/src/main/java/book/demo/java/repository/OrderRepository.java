package book.demo.java.repository;

import book.demo.java.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByReaderId(int readerId);

}
