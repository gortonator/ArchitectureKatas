package book.demo.java.service;

import book.demo.java.model.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<Book> getAllBooks();

    Map<String, Object> getBooksWithPaging(int page, int size);

    Integer createBook(Book book);

    Book getBookById(int bookId);

    List<Book> getBooksByAuthor(String author);

    void deleteBookById(int bookId);
}
