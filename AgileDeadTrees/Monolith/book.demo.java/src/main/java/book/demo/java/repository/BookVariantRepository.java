package book.demo.java.repository;

import book.demo.java.entity.book.published.BookVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookVariantRepository extends JpaRepository<BookVariant, Integer> {
}
