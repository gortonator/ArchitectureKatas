package book.demo.java.service;

import book.demo.java.entity.book.published.PublishedBook;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PublishedBookService {

    List<PublishedBook> getAllBooks();

    Page<PublishedBook> getBooksWithPaging(int page, int size);

    Integer createBook(PublishedBook book);

    Page<PublishedBook> findByKeywordContaining(String keyword, int page, int size);

    PublishedBook getBookById(int bookId);

    List<PublishedBook> getBooksByAuthor(String author);

    void deleteBookById(int bookId);
}
