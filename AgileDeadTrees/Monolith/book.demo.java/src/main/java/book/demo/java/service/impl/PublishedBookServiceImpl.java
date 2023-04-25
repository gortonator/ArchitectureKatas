package book.demo.java.service.impl;

import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.repository.AuthorRepository;
import book.demo.java.repository.PublishedBookRepository;
import book.demo.java.service.PublishedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Transactional
@Service
public class PublishedBookServiceImpl implements PublishedBookService {

    @Autowired
    private PublishedBookRepository publishedBookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Override
    public List<PublishedBook> getAllBooks() {
        return publishedBookRepo.findAll();
    }

    @Override
    public Page<PublishedBook> getBooksWithPaging(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return publishedBookRepo.findAll(paging);
    }

    @Override
    public Integer createBook(PublishedBook book) {
        return publishedBookRepo.save(book).getId();
    }

    @Override
    public Page<PublishedBook> findByKeywordContaining(String keyword, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return publishedBookRepo.findByKeywordContainingIgnoreCase(keyword, paging);
    }

    @Override
    public PublishedBook getBookById(int bookId) {
        return publishedBookRepo.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book id " + bookId + " NOT FOUND."));
    }

    @Override
    public List<PublishedBook> getBooksByAuthor(String author) {
        return publishedBookRepo.findByAuthors(author);
    }

    @Override
    public void deleteBookById(int bookId) {
        PublishedBook bookToDelete = getBookById(bookId);

        Set<Author> authors = bookToDelete.getAuthors();
        for (Author author : authors) {
            author.removePublishedBook(bookToDelete);
            authorRepo.save(author);
        }

        publishedBookRepo.delete(bookToDelete);
    }

}

