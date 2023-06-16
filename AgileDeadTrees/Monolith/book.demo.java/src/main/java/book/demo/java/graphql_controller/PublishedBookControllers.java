package book.demo.java.graphql_controller;

import java.util.List;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.service.PublishedBookService;

@Controller
public class PublishedBookControllers {

    private PublishedBookService publishedBookService;

    public PublishedBookControllers(PublishedBookService publishedBookService) {
        this.publishedBookService = publishedBookService;
    }

    @QueryMapping
    public List<PublishedBook> getAllBooks() {
        return publishedBookService.getAllBooks();
    }

    @QueryMapping
    public Page<PublishedBook> getBooksWithPaging(@Argument Integer page, @Argument Integer size) {
       return publishedBookService.getBooksWithPaging(page, size);
   }

   @QueryMapping
    public Page<PublishedBook> findByKeywordContaining(@Argument String keyword, @Argument Integer page, @Argument Integer size) {
       return publishedBookService.findByKeywordContaining(keyword, page, size);
   }

   @QueryMapping
   public PublishedBook getBookById(@Argument Integer id) {
       return publishedBookService.getBookById(id);
   }

   @QueryMapping
   public List<PublishedBook> getBooksByAuthor(@Argument String author) {
       return publishedBookService.getBooksByAuthor(author);
   }

   @MutationMapping
   public Integer createBook(@Argument PublishedBook book) {
       return publishedBookService.createBook(book);
   }

   @MutationMapping
   public void deleteBookById(@Argument Integer id) {
    publishedBookService.deleteBookById(id);
   }
}
