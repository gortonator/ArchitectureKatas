package book.demo.java.repository;

import book.demo.java.entity.book.editorial.EditorComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditorCommentRepository extends JpaRepository<EditorComment, Integer> {

    List<EditorComment> findByReviewId(int reviewId);
}
