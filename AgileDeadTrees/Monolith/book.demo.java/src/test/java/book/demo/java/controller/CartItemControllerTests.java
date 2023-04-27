package book.demo.java.controller;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import book.demo.java.entity.book.published.BookFormat;
import book.demo.java.entity.book.published.BookVariant;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.entity.cart.CartItem;
import book.demo.java.service.CartItemService;
import book.demo.java.service.ReaderService;
import book.demo.java.util.AuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartItemController.class)
public class CartItemControllerTests {

    private final String username = "reader";
    private final String password = "password";
    private final String email = "email";
    private String baseUrl;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartItemService cartItemService;
    @MockBean
    private ReaderService readerService;
    private Reader reader1;

    private Author author1;
    private Author author2;

    private PublishedBook publishedBook1;
    private PublishedBook publishedBook2;

    private BookVariant bookVariant1;
    private BookVariant bookVariant2;
    private BookVariant bookVariant3;

    private List<CartItem> cartItems;
    private CartItem cartItem1;
    private CartItem cartItem2;

    private MockedStatic<AuthUtil> authUtil;

    @BeforeEach
    void setup() {
        baseUrl = "/api/cart";

        reader1 = new Reader(username, password, email);
        reader1.setId(1);
        author1 = new Author("author1", "author1");
        author1.setId(1);
        author2 = new Author("author2", "author2");
        author2.setId(2);
        Set<Author> authors1 = new HashSet<>();
        authors1.add(author1);
        Set<Author> authors2 = new HashSet<>();
        authors2.add(author1);
        authors2.add(author2);

        publishedBook1 = new PublishedBook("title1", Genre.ROMANCE, authors1);
        publishedBook1.setId(1);
        publishedBook2 = new PublishedBook("title2", Genre.ROMANCE, authors2);
        publishedBook1.setId(2);
        bookVariant1 = new BookVariant(publishedBook1, "1".repeat(13), BookFormat.EBOOK,
                LocalDate.now(), BigDecimal.valueOf(13));
        bookVariant1.setId(1);
        bookVariant2 = new BookVariant(publishedBook1, "2".repeat(13), BookFormat.PAPERBACK,
                LocalDate.now(), BigDecimal.valueOf(15));
        bookVariant2.setId(2);
        bookVariant3 = new BookVariant(publishedBook2, "3".repeat(13), BookFormat.PAPERBACK,
                LocalDate.now(), BigDecimal.valueOf(2));
        bookVariant3.setId(3);
        cartItem1 = new CartItem(reader1, bookVariant1, 13);
        cartItem1.setId(1);
        cartItem2 = new CartItem(reader1, bookVariant2, 15);
        cartItem2.setId(2);
        cartItems = Arrays.asList(cartItem1, cartItem2);

        authUtil = Mockito.mockStatic(AuthUtil.class);
    }

    @AfterEach
    void cleanup() {
        authUtil.close();
    }

    @Test
    public void testListCartItemForReader() throws Exception {

        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        when(readerService.findReaderByUsername(username)).thenReturn(reader1);
        when(cartItemService.getCartItemsByReaderUsername(username))
                .thenReturn(cartItems);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(cartItems);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void testListCartItem() throws Exception {
        String url = baseUrl + "/1";
        when(cartItemService.getCartItemsByReaderId(1))
                .thenReturn(cartItems);

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(cartItems);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void testAddBookToCart() throws Exception {
        authUtil.when(AuthUtil::getAuthenticatedUsername).thenReturn(username);

        CartItem cartItem = new CartItem(reader1, bookVariant3, 2);
        when(readerService.findReaderByUsername(username)).thenReturn(reader1);
        when(cartItemService.addCartItem(bookVariant3.getId(), 2, username))
                .thenReturn(cartItem);

        String url = baseUrl + "/add";
        MvcResult mvcResult = mockMvc.perform(post(url)
                        .param("bookVariantId", "3")
                        .param("quantity", "2"))
                .andExpect(status().isCreated())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(cartItem);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }
}
