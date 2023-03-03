package book.demo.java.controller;

import book.demo.java.exception.ExceptionUtil;
import book.demo.java.model.Book;
import book.demo.java.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get all books.")
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all books with pagination.")
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getBooksWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        try {
            Map<String, Object> response = bookService.getBooksWithPaging(page, size);
// attention
//            if (books.isEmpty()) {
//                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new book.")
    @PostMapping
    public ResponseEntity<Integer> createBook(@Valid @RequestBody Book book) {
        try {
            Integer bookId = bookService.createBook(book);
            return new ResponseEntity<>(bookId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a book by bookId.")
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
        try {
            Book book = bookService.getBookById(bookId);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all books by author name.")
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        try {
            List<Book> booksByAuthor = bookService.getBooksByAuthor(author);

            if (booksByAuthor.isEmpty()) {
                return new ResponseEntity<>(booksByAuthor, HttpStatus.OK);
            }
            return new ResponseEntity<>(booksByAuthor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a book by id.")
    @DeleteMapping("/book/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable int bookId) {
        try {
            bookService.deleteBookById(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
