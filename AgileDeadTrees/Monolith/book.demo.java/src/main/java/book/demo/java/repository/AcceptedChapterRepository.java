package book.demo.java.repository;

import book.demo.java.entity.book.editorial.AcceptedChapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptedChapterRepository extends JpaRepository<AcceptedChapter, Integer> {
}
