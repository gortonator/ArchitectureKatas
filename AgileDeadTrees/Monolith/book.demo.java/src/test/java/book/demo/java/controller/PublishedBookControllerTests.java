package book.demo.java.controller;

import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.service.PublishedBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublishedBookController.class)
public class PublishedBookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PublishedBookService publishedBookService;

    private final String baseUrl = "/api/books/published";
    ;

    private PublishedBook publishedBook1;

    private List<PublishedBook> publishedBooks;


    @BeforeEach
    void setup() {

        Author author1 = new Author("author1", "author1");
        Author author2 = new Author("author2", "author2");

        Set<Author> authors1 = new HashSet<>();
        authors1.add(author1);
        Set<Author> authors2 = new HashSet<>();
        authors2.add(author1);
        authors2.add(author2);

        publishedBook1 = new PublishedBook("Book1", Genre.ROMANCE, authors1);
        PublishedBook publishedBook2 = new PublishedBook("Book2", Genre.ROMANCE, authors2);

        publishedBooks = new ArrayList<>();
        publishedBooks.add(publishedBook1);
        publishedBooks.add(publishedBook2);

    }


    @Test
    public void testGetAllBooks() throws Exception {
        when(publishedBookService.getAllBooks()).thenReturn(publishedBooks);
        String url = baseUrl + "/all";
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        verify(publishedBookService).getAllBooks();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(publishedBooks);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }

    @Test
    public void testGetAllBooksNoContent() throws Exception {
        when(publishedBookService.getAllBooks()).thenReturn(new ArrayList<>());
        String url = baseUrl + "/all";
        mockMvc.perform(get(url))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testGetBookById() throws Exception {

        int bookId = publishedBook1.getId();
        when(publishedBookService.getBookById(bookId)).thenReturn(publishedBook1);

        String url = baseUrl + "/{bookId}";
        MvcResult mvcResult = mockMvc.perform(get(url, bookId))
                .andExpect(status().isOk())
                .andReturn();
        verify(publishedBookService).getBookById(bookId);

        String expectedResponseBody = new ObjectMapper().writeValueAsString(publishedBook1);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponseBody);
    }
}
