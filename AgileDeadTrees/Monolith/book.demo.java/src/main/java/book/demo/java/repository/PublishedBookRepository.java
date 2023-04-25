package book.demo.java.repository;

import book.demo.java.entity.book.published.PublishedBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublishedBookRepository extends JpaRepository<PublishedBook, Integer> {

    List<PublishedBook> findByAuthors(String authors);

    Page<PublishedBook> findAll(Pageable pageable);

    @Query("SELECT pb FROM PublishedBook pb LEFT JOIN pb.authors a WHERE LOWER(CONCAT(a.firstName, ' ', a.lastName)) " +
            "LIKE CONCAT('%', LOWER(:keyword), '%') OR LOWER(pb.title) LIKE CONCAT('%', LOWER(:keyword), '%')")
    Page<PublishedBook> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

}
