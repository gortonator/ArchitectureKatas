package book.demo.java.repository;

import book.demo.java.entity.order.OrderTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTrackRepository extends JpaRepository<OrderTrack, Integer> {
}
