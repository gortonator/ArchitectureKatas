/**
 * This is a controller class handling HTTP requests related to PublishedBook entity.
 * <p>
 * Endpoints:
 * GET /api/books/published/all: Get all published books.
 * GET /api/books/published: Get all published books with pagination.
 * GET /api/books/published/{bookId}: Get a published book by bookId.
 * POST /api/books/published/create: Create a new published book.
 * GET /api/books/published/s: Get all published books by author name and title keyword.
 * DELETE /api/books/published/remove/{bookId}: Delete a published book by id.
 *
 * @author: Tong
 * @see: PredefinedRoles
 */

package book.demo.java.controller;

import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.service.PublishedBookService;
import book.demo.java.util.PredefinedRole;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books/published")
public class PublishedBookController {

    @Autowired
    private PublishedBookService publishedBookService;

    @Operation(summary = "Get all published books.")
    @GetMapping("/all")
    public ResponseEntity<List<PublishedBook>> getAllBooks() {
        List<PublishedBook> books = publishedBookService.getAllBooks();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Get all published books with pagination.")
    @GetMapping
    public ResponseEntity<Page<PublishedBook>> getBooksWithPaging(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        Page<PublishedBook> pageBooks = publishedBookService.getBooksWithPaging(page, size);
        if (!pageBooks.hasContent()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageBooks, HttpStatus.OK);

    }

    @Operation(summary = "Get a published book by bookId.")
    @GetMapping("/{bookId}")
    public ResponseEntity<PublishedBook> getBookById(@PathVariable int bookId) {
        PublishedBook book = publishedBookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Create a new published book.")
    @PostMapping("/create")
    @RequiresRoles(PredefinedRole.MANAGER_ROLE)
    public ResponseEntity<Integer> createBook(@RequestBody PublishedBook book) {
        Integer bookId = publishedBookService.createBook(book);
        return new ResponseEntity<>(bookId, HttpStatus.CREATED);
    }


    @Operation(summary = "Get all published books by author name and title keyword.")
    @GetMapping("/s")
    public ResponseEntity<Page<PublishedBook>> getBooksByAuthorNameKeyword(@RequestParam(name = "k") String keyword,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "5") int size) {
        Page<PublishedBook> pageBooksByAuthor = publishedBookService.findByKeywordContaining(keyword, page, size);

        if (pageBooksByAuthor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageBooksByAuthor, HttpStatus.OK);
    }

    @Operation(summary = "Delete a published book by id.")
    @DeleteMapping("/remove/{bookId}")
    @RequiresRoles(PredefinedRole.MANAGER_ROLE)
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable int bookId) {
        publishedBookService.deleteBookById(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
