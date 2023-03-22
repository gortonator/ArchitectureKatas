package book.demo.java.repository;

import book.demo.java.model.Book;
import book.demo.java.model.CartItem;
import book.demo.java.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByReaderId(int readerId);

    List<CartItem> findByReader(Reader reader);

    CartItem findByReaderAndBook(Reader reader, Book book);

    CartItem findByReaderIdAndBookId(int readerId, int bookId);

    void deleteByReaderIdAndBookId(int readerId, int bookId);

    void deleteByReaderId(int readerId);
}
