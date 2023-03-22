package book.demo.java.repository;

import book.demo.java.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByAuthor(String author);
    Page<Book> findAll(Pageable pageable);
}
