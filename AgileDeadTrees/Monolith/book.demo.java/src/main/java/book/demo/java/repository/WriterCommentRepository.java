package book.demo.java.repository;

import book.demo.java.entity.book.editorial.WriterComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WriterCommentRepository extends JpaRepository<WriterComment, Integer> {

    List<WriterComment> findByReviewId(int reviewId);
}
