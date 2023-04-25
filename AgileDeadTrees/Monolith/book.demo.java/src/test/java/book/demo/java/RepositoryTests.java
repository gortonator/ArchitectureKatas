package book.demo.java;

import book.demo.java.entity.account.Address;
import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.Permission;
import book.demo.java.entity.account.internal.Role;
import book.demo.java.entity.account.internal.User;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import book.demo.java.entity.book.editorial.*;
import book.demo.java.entity.book.published.BookFormat;
import book.demo.java.entity.book.published.BookVariant;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.entity.cart.CartItem;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.order.Order;
import book.demo.java.entity.order.OrderDetail;
import book.demo.java.entity.order.OrderStatus;
import book.demo.java.entity.order.OrderTrack;
import book.demo.java.repository.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    private enum RoleType {
        ADMIN, MANAGER, EDITOR
    }

    /*
    Repositories listing
     */
    @Autowired
    private AcceptedChapterRepository acceptedChapterRepo;

    @Autowired
    private BookVariantRepository bookVariantRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private OrderTrackRepository orderTrackRepo;

    @Autowired
    private EditorCommentRepository editorCommentRepo;

    @Autowired
    private WriterCommentRepository writerCommentRepo;

    @Autowired
    private DraftBookRepository draftBookRepo;

    @Autowired
    private DraftChapterRepository draftChapterRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private PermissionRepository permissionRepo;

    @Autowired
    private PublishedBookRepository publishedBookRepo;

    @Autowired
    private ReaderRepository readerRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private WriterRepository writerRepo;

    @Autowired
    private AuthorRepository authorRepo;

    /*
    Helper Variables
     */ String placeholder;
    String passwordString;
    String email;
    String one;
    String two;
    String three;
    String four;
    int idxOne;
    int idxTwo;
    int idxThree;
    int idxFour;

    /*
    Entities to insert into database.
     */

    // Permissions
    private List<Permission> permissions;

    // Roles
    private List<Role> roles;
    private Role adminRole;
    private Role managerRole;
    private Role editorRole;

    // Address
    private Address address1;
    private Address address2;

    // Readers
    private List<Reader> readers;
    private Reader reader1;
    private Reader reader2;

    // Users
    private List<User> users;
    private User admin;
    private User manager;
    private User editor;

    // Authors
    private List<Author> authors;
    private Author author1;
    private Author author2;
    private Author author3;

    // Writers
    private List<Writer> writers;
    private Writer writer1;
    private Writer writer2;

    // PublishedBooks
    private List<PublishedBook> publishedBooks;
    private PublishedBook publishedBook1;
    private PublishedBook publishedBook2;
    private PublishedBook publishedBook3;

    // BookVariants
    private List<BookVariant> bookVariants;
    private BookVariant bookVariant1;
    private BookVariant bookVariant2;
    private BookVariant bookVariant3;
    private BookVariant bookVariant4;

    // CartItems
    private List<CartItem> cartItems;
    private CartItem cartItem1;
    private CartItem cartItem2;
    private CartItem cartItem3;

    // Orders
    private List<Order> orders;
    private Order order1;
    private Order order2;

    // DraftBooks
    private List<DraftBook> draftBooks;
    private DraftBook draftBook1;
    private DraftBook draftBook2;

    // DraftChapters
    private List<DraftChapter> draftChapters;
    private DraftChapter draftChapter1;
    private DraftChapter draftChapter2;
    private DraftChapter draftChapter3;

    // AcceptedChapters
    private List<AcceptedChapter> acceptedChapters;
    private AcceptedChapter acceptedChapter1;
    private AcceptedChapter acceptedChapter2;

    // Reviews
    private List<Review> reviews;
    private Review review1;
    private Review review2;

    // Comments
    private List<EditorComment> editorComments;
    private List<WriterComment> writerComments;
    private EditorComment editorComment1;
    private WriterComment writerComment1;

    @BeforeEach
    void setUp() {
        placeholder = "";
        passwordString = "password";
        email = "grabacorncob@gmail.com";
        one = "1";
        two = "2";
        three = "3";
        four = "4";
        idxOne = 1;
        idxTwo = 2;
        idxThree = 3;
        idxFour = 4;
    }

    @Test // Permissions
    public void savePermissions() {
        permissions = new ArrayList<>();
        List<String> permsPrefix = Arrays.asList("book", "user", "order");
        List<String> permsSuffix = Arrays.asList("create", "read", "update", "delete");
        for (String permPrefix : permsPrefix) {
            for (String permSuffix : permsSuffix) {
                Permission perm = new Permission(permPrefix + ":" + permSuffix, permSuffix + " " + permPrefix);
                permissions.add(perm);
            }
        }
        permissionRepo.saveAll(permissions);
    }

    @Test // Roles
    public void saveRoles() {
        List<Permission> permissions = permissionRepo.findAll();

        adminRole = new Role(RoleType.ADMIN.name(), "admin role");
        for (Permission perm : permissions) {
            adminRole.addPermission(perm);
        }
        managerRole = new Role(RoleType.MANAGER.name(), "manager role");
        for (Permission perm : permissions.subList(8, 12)) {
            managerRole.addPermission(perm);
        }
        editorRole = new Role(RoleType.EDITOR.name(), "editor role");
        for (Permission perm : permissions.subList(0, 4)) {
            editorRole.addPermission(perm);
        }
        roles = Arrays.asList(adminRole, managerRole, editorRole);

        roleRepo.saveAll(roles);
    }

    @Test // Readers
    public void saveReaders() {
        placeholder = "reader";
        String enPassword1 = new SimpleHash("MD5", passwordString + one, placeholder + one, 1024).toString();
        String enPassword2 = new SimpleHash("MD5", passwordString + two, placeholder + two, 1024).toString();
        reader1 = new Reader(placeholder + one, placeholder + one, placeholder + one, enPassword1, email);
        address1 = new Address(reader1.getFirstName(), reader1.getLastName(), "phone",
                "addressLine1", "addressLine2", "Seattle", "WA", one.repeat(5), reader1);
        reader2 = new Reader(placeholder + two, placeholder + two, placeholder + two, enPassword2, email);
        address2 = new Address(reader2.getFirstName(), reader2.getLastName(), "phone",
                "addressLine1", "addressLine2", "Seattle", "WA", two.repeat(5), reader2);
        reader2.setAddress(address2);
        readers = Arrays.asList(reader1, reader2);

        addressRepo.save(address1);
        addressRepo.save(address2);
        readerRepo.saveAll(readers);
    }

    @Test // Users
    public void saveUsers() {

        adminRole = roleRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        assertThat(adminRole.getName()).isEqualTo(RoleType.ADMIN.name());
        managerRole = roleRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        assertThat(managerRole.getName()).isEqualTo(RoleType.MANAGER.name());
        editorRole = roleRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);
        assertThat(editorRole.getName()).isEqualTo(RoleType.EDITOR.name());

        placeholder = "admin";
        String enAdminPassword = new SimpleHash("MD5", placeholder + passwordString, placeholder, 1024).toString();
        admin = new User(placeholder, placeholder, placeholder, enAdminPassword, email);
        admin.addRole(adminRole);

        placeholder = "manager";
        String enManagerPassword = new SimpleHash("MD5", placeholder + passwordString, placeholder, 1024).toString();
        manager = new User(placeholder, placeholder, placeholder, enManagerPassword, email);
        manager.addRole(managerRole);

        placeholder = "editor";
        String enEditorPassword = new SimpleHash("MD5", placeholder + passwordString, placeholder, 1024).toString();
        editor = new User(placeholder, placeholder, placeholder, enEditorPassword, email);
        editor.addRole(editorRole);

        users = Arrays.asList(admin, manager, editor);
        userRepo.saveAll(users);
    }

    @Test
    public void saveAuthors() {
        placeholder = "author";
        author1 = new Author(placeholder + one, placeholder + one);
        author2 = new Author(placeholder + two, placeholder + two);
        author3 = new Author(placeholder + three, placeholder + three);
        authors = Arrays.asList(author1, author2, author3);

        authorRepo.saveAll(authors);
    }

    @Test // Writers
    public void saveWriters() {
        placeholder = "writer";
        author1 = authorRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        author2 = authorRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);

        String enPassword1 = new SimpleHash("MD5", passwordString + one, placeholder + one, 1024).toString();
        String enPassword2 = new SimpleHash("MD5", passwordString + two, placeholder + two, 1024).toString();

        writer1 = new Writer(placeholder + one, enPassword1, email, author1);
        writer2 = new Writer(placeholder + two, enPassword2, email, author2);
        writers = Arrays.asList(writer1, writer2);

        writerRepo.saveAll(writers);
    }

    @Test // PublishedBooks
    public void savePublishedBooks() {
        placeholder = "publishedBook";
        author1 = authorRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        author2 = authorRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        author3 = authorRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);

        Set<Author> authors1 = Stream.of(author1).collect(Collectors.toSet());
        Set<Author> authors2 = Stream.of(author1, author2).collect(Collectors.toSet());
        Set<Author> authors3 = Stream.of(author3).collect(Collectors.toSet());

        publishedBook1 = new PublishedBook(placeholder + one, Genre.FANTASY, authors1);
        publishedBook2 = new PublishedBook(placeholder + two, Genre.HORROR, authors2);
        publishedBook3 = new PublishedBook(placeholder + three, Genre.ROMANCE, authors3);

        publishedBooks = Arrays.asList(publishedBook1, publishedBook2, publishedBook3);
        publishedBookRepo.saveAll(publishedBooks);
    }

    @Test // BookVariants
    public void saveBookVariants() {
        placeholder = "bookVariant";
        publishedBook1 = publishedBookRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        publishedBook2 = publishedBookRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        publishedBook3 = publishedBookRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);

        LocalDate publishedDate1 = LocalDate.of(1990, 3, 16);
        bookVariant1 = new BookVariant(publishedBook1, one.repeat(13), BookFormat.PAPERBACK,
                publishedDate1, BigDecimal.valueOf(15.99));
        LocalDate publishedDate2 = LocalDate.of(2007, 5, 16);
        bookVariant2 = new BookVariant(publishedBook1, two.repeat(13), BookFormat.EBOOK,
                publishedDate2, BigDecimal.valueOf(5.99));
        LocalDate publishedDate3 = LocalDate.of(2013, 7, 3);
        bookVariant3 = new BookVariant(publishedBook2, three.repeat(13), BookFormat.EBOOK,
                publishedDate3, BigDecimal.valueOf(15.00));
        LocalDate publishedDate4 = LocalDate.of(2022, 12, 5);
        bookVariant4 = new BookVariant(publishedBook3, four.repeat(13), BookFormat.HARDCOVER,
                publishedDate4, BigDecimal.valueOf(7));
        bookVariants = Arrays.asList(bookVariant1, bookVariant2, bookVariant3, bookVariant4);

        bookVariantRepo.saveAll(bookVariants);
    }

    @Test // CartItems
    public void saveCartItems() {
        reader1 = readerRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        reader2 = readerRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);

        bookVariant1 = bookVariantRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        bookVariant3 = bookVariantRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);

        cartItem1 = new CartItem(reader1, bookVariant1, 2);
        cartItem2 = new CartItem(reader1, bookVariant3, 1);
        cartItem3 = new CartItem(reader2, bookVariant3, 1);
        cartItems = Arrays.asList(cartItem1, cartItem2, cartItem3);

        cartItemRepo.saveAll(cartItems);
    }

    @Test // Orders
    public void saveOrders() {
        // Order 1
        reader2 = readerRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        bookVariant1 = bookVariantRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);

        CartItem tempCartItem1 = new CartItem(reader2, bookVariant1, 2);
        Checkout tempCheckout1 = new Checkout(List.of(tempCartItem1));

        order1 = new Order(reader2);
        order1.setAddress(reader2.getAddress());
        order1.setPaymentType(tempCheckout1.getPaymentType());
        order1.setShippingType(tempCheckout1.getShippingType());
        order1.setShippingFee(tempCheckout1.getShippingFee());
        order1.setSubtotal(tempCheckout1.getSubtotal());
        order1.setNumOfItems(tempCheckout1.getNumOfItems());
        order1.setGrandTotal(tempCheckout1.getGrandTotal());
        order1.setDeliverDays(tempCheckout1.getDeliverDays());
        order1.setExpectedDeliverDate(order1.getDatetimeCreated().toLocalDate().plusDays(tempCheckout1.getDeliverDays()));
        order1.setOrderStatus(OrderStatus.SHIPPED);
        Order createdOrder1 = orderRepo.save(order1);
        List<OrderDetail> orderDetails1 = new ArrayList<>();
        for (CartItem cartItem : tempCheckout1.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail(
                    cartItem.getBookVariant(),
                    createdOrder1,
                    cartItem.getQuantity());
            orderDetails1.add(orderDetail);
        }
        createdOrder1.setOrderDetails(orderDetails1);

        // Order 2
        bookVariant2 = bookVariantRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        bookVariant3 = bookVariantRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);

        CartItem tempCartItem2 = new CartItem(reader2, bookVariant2, 2);
        CartItem tempCartItem3 = new CartItem(reader2, bookVariant3, 10);
        Checkout tempCheckout2 = new Checkout(List.of(tempCartItem2, tempCartItem3));

        order2 = new Order(reader2);
        order2.setAddress(reader2.getAddress());
        order2.setPaymentType(tempCheckout2.getPaymentType());
        order2.setShippingType(tempCheckout2.getShippingType());
        order2.setShippingFee(tempCheckout2.getShippingFee());
        order2.setSubtotal(tempCheckout2.getSubtotal());
        order2.setNumOfItems(tempCheckout2.getNumOfItems());
        order2.setGrandTotal(tempCheckout2.getGrandTotal());
        order2.setDeliverDays(tempCheckout2.getDeliverDays());
        order2.setExpectedDeliverDate(order2.getDatetimeCreated().toLocalDate().plusDays(tempCheckout2.getDeliverDays()));
        order2.setOrderStatus(OrderStatus.DELIVERED);

        Order createdOrder2 = orderRepo.save(order2);
        List<OrderDetail> orderDetails2 = new ArrayList<>();
        for (CartItem cartItem : tempCheckout2.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail(
                    cartItem.getBookVariant(),
                    createdOrder2,
                    cartItem.getQuantity());
            orderDetails2.add(orderDetail);
        }
        createdOrder2.setOrderDetails(orderDetails2);

        orders = Arrays.asList(createdOrder1, createdOrder1);
        orderRepo.saveAll(orders);

        OrderTrack orderTrack1 = new OrderTrack(order1,"Shipped.");
        orderTrackRepo.save(orderTrack1);
    }

    @Test // DraftBooks
    public void saveDraftBooks() {
        placeholder = "draftBook";
        author1 = authorRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        author2 = authorRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        author3 = authorRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);

//        Set<Author> authors1 = Stream.of(author1).collect(Collectors.toSet());
        Set<Author> authors2 = Stream.of(author1, author2).collect(Collectors.toSet());
        Set<Author> authors3 = Stream.of(author3).collect(Collectors.toSet());

        draftBook1 = new DraftBook(placeholder + one, Genre.ROMANCE, authors2);
        draftBook2 = new DraftBook(placeholder + two, Genre.ROMANCE, authors3);

        draftBooks = Arrays.asList(draftBook1, draftBook2);
        draftBookRepo.saveAll(draftBooks);
    }

    @Test // DraftChapters
    public void saveDraftChapters() {
        placeholder = "title";
        String content = "content";

        author1 = authorRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        author2 = authorRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        author3 = authorRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);

        draftBook1 = draftBookRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        draftBook2 = draftBookRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);

        draftChapter1 = new DraftChapter(3, placeholder, content, draftBook1, author1);
        draftChapter2 = new DraftChapter(4, placeholder, content, draftBook1, author2);
        draftChapter3 = new DraftChapter(1, placeholder, content, draftBook2, author3);

        draftChapters = Arrays.asList(draftChapter1, draftChapter2, draftChapter3);
        draftChapterRepo.saveAll(draftChapters);
    }

    @Test // AcceptedChapters
    public void saveAcceptedChapters() {
        placeholder = "title";
        String content = "content";

        author1 = authorRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        draftBook1 = draftBookRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);

        acceptedChapter1 = new AcceptedChapter(1, placeholder, content, draftBook1, author1);
        acceptedChapter2 = new AcceptedChapter(2, placeholder, content, draftBook1, author1);

        acceptedChapters = Arrays.asList(acceptedChapter1, acceptedChapter2);
        acceptedChapterRepo.saveAll(acceptedChapters);
    }

    @Test // Reviews
    public void saveReviews() {
        String content = "content";
        placeholder = "editor";

        draftChapter1 = draftChapterRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        draftChapter2 = draftChapterRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        editor = userRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);
        assertThat(editor.getFirstName()).isEqualTo(placeholder);

        review1 = new Review(draftChapter1, editor, content);
        review2 = new Review(draftChapter2, editor, content);

        reviews = Arrays.asList(review1, review2);
        reviewRepo.saveAll(reviews);
    }

    @Test
    public void saveEditorComments() {
        String content = "content";
        editor = userRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);
        review1 = reviewRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);

        editorComment1 = new EditorComment(content, review1, editor);
        editorComments = Collections.singletonList(editorComment1);
        editorCommentRepo.saveAll(editorComments);
    }

    @Test
    public void saveWriterComments() {
        String content = "content";
        writer1 = writerRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);
        review1 = reviewRepo.findById(idxOne).orElseThrow(NoSuchElementException::new);

        writerComment1 = new WriterComment(content, review1, writer1);
        writerComments = Collections.singletonList(writerComment1);
        writerCommentRepo.saveAll(writerComments);
    }


    /*
    For supplementary testing.
     */
    @Test // CartItems
    public void saveCartItemsForReader2() {
        reader2 = readerRepo.findById(idxTwo).orElseThrow(NoSuchElementException::new);
        bookVariant3 = bookVariantRepo.findById(idxThree).orElseThrow(NoSuchElementException::new);

        cartItem3 = new CartItem(reader2, bookVariant3, 1);
        cartItems = List.of(cartItem3);

        cartItemRepo.saveAll(cartItems);
    }
}