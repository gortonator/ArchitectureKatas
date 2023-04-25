package book.demo.java.service.impl;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import book.demo.java.entity.book.published.BookFormat;
import book.demo.java.entity.book.published.BookVariant;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.entity.cart.CartItem;
import book.demo.java.repository.BookVariantRepository;
import book.demo.java.repository.CartItemRepository;
import book.demo.java.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private BookVariantRepository bookVariantRepository;

    @Mock
    private ReaderRepository readerRepository;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    private final String username = "reader";
    private final String password = "password";
    private final String email = "email";

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

    @BeforeEach
    void setup() {

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

    }

    @DisplayName("JUnit test for getCartItemsByReaderId method")
    @Test
    public void getCartItemsByReaderIdTest() {
        int readerId = reader1.getId();
        when(cartItemRepository.findByReaderId(readerId)).thenReturn(cartItems);

        List<CartItem> cartItemList = cartItemService.getCartItemsByReaderId(readerId);

        assertThat(cartItemList).isNotNull();
        assertThat(cartItemList.size()).isEqualTo(2);
    }
}