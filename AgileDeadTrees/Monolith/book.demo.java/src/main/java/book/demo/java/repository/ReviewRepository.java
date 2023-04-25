package book.demo.java.repository;

import book.demo.java.entity.book.editorial.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
