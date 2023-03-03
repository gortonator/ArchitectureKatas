package book.demo.java.service;

import book.demo.java.exception.BookNotFoundException;
import book.demo.java.model.Book;
import book.demo.java.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<Book>(bookRepository.findAll());
    }

    @Override
    public Map<String, Object> getBooksWithPaging(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Book> pageBooks = bookRepository.findAll(paging);
        List<Book> books = pageBooks.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("books", books);
        response.put("currentPage", pageBooks.getNumber());
        response.put("totalItems", pageBooks.getTotalElements());
        response.put("totalPages", pageBooks.getTotalPages());
        return response;
    }

    @Override
    public Integer createBook(Book book) {
        return bookRepository.save(book).getBookId();
    }

    @Override
    public Book getBookById(int bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book id " + bookId + " NOT FOUND."));
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return new ArrayList<Book>(bookRepository.findByAuthor(author));
    }

    @Override
    public void deleteBookById(int bookId) {
        bookRepository.deleteById(bookId);
    }

}

