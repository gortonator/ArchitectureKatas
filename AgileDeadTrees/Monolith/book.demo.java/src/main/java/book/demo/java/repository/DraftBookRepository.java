package book.demo.java.repository;

import book.demo.java.entity.book.editorial.DraftBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DraftBookRepository extends JpaRepository<DraftBook, Integer> {

    List<DraftBook> findByAuthors(String authors);

    Page<DraftBook> findByAuthors(String authors, Pageable pageable);

    Page<DraftBook> findAll(Pageable pageable);

    Page<DraftBook> findByTitleContaining(String title, Pageable pageable);

    List<DraftBook> findByAuthorsContaining(String authors, Pageable pageable);
}
