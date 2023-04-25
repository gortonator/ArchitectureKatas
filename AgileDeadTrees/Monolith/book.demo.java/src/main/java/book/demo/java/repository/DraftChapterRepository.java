package book.demo.java.repository;

import book.demo.java.entity.book.editorial.DraftChapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DraftChapterRepository extends JpaRepository<DraftChapter, Integer> {
}
