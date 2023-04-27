package book.demo.java.controller;

import book.demo.java.entity.account.Address;
import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import book.demo.java.entity.book.published.BookFormat;
import book.demo.java.entity.book.published.BookVariant;
import book.demo.java.entity.book.published.PublishedBook;
import book.demo.java.entity.cart.PaymentType;
import book.demo.java.entity.cart.ShippingType;
import book.demo.java.entity.order.Order;
import book.demo.java.entity.order.OrderDetail;
import book.demo.java.entity.order.OrderStatus;
import book.demo.java.entity.order.OrderTrack;
import book.demo.java.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    private final String baseUrl = "/api/orders";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    private Reader reader1;

    private Order order1;

    @BeforeEach
    void setup() {
        reader1 = new Reader("username", "password", ":email");
        Address address = new Address("name", "name", "phone", "addressLine1",
                "addressLine2", "city", "state", "postalCode",
                reader1);
        reader1.setAddress(address);
        String placeholder = "author";
        String one = "1";
        Author author1 = new Author(placeholder + one, placeholder + one);
        Set<Author> authors1 = Stream.of(author1).collect(Collectors.toSet());
        PublishedBook publishedBook1 = new PublishedBook(placeholder + one, Genre.FANTASY, authors1);
        LocalDate publishedDate1 = LocalDate.of(1990, 3, 16);
        BookVariant bookVariant1 = new BookVariant(publishedBook1, one.repeat(13), BookFormat.PAPERBACK,
                publishedDate1, BigDecimal.valueOf(15.99));

        order1 = new Order(reader1);
        order1.setAddress(reader1.getAddress());
        OrderDetail orderDetail = new OrderDetail(bookVariant1, order1, 1);
        order1.setOrderDetails(List.of(orderDetail));
        order1.setPaymentType(PaymentType.CARD);
        order1.setShippingType(ShippingType.STANDARD);
        order1.setShippingFee(ShippingType.STANDARD.getShippingFee());
        order1.setSubtotal(bookVariant1.getPrice());
        order1.setNumOfItems(orderDetail.getQuantity());
        order1.setGrandTotal(bookVariant1.getPrice());
        order1.setDeliverDays(ShippingType.STANDARD.getDeliverDays());
        order1.setExpectedDeliverDate(order1.getDatetimeCreated().toLocalDate()
                .plusDays(ShippingType.STANDARD.getDeliverDays()));
    }

    @Test
    public void listOrdersTest() throws Exception {
        List<Order> orders = Collections.singletonList(order1);
        when(orderService.getOrdersByReaderID(reader1.getId())).thenReturn(orders);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                        .param("readerId", String.valueOf(reader1.getId())))
                .andExpect(status().isOk())
                .andReturn();

        verify(orderService).getOrdersByReaderID(reader1.getId());

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(orders);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        int orderId = order1.getId();
        when(orderService.getOrder(orderId)).thenReturn(order1);

        String url = baseUrl + "/{orderId}";
        MvcResult mvcResult = mockMvc.perform(get(url, orderId))
                .andExpect(status().isOk())
                .andReturn();

        verify(orderService).getOrder(orderId);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(order1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void setOrderStatusByIdTest() throws Exception {
        int orderId = order1.getId();
        OrderStatus orderStatus = OrderStatus.PROCESSING;
        order1.setOrderStatus(orderStatus);

        when(orderService.setOrderStatus(orderId, orderStatus)).thenReturn(order1);

        String url = baseUrl + "/{orderId}/status";
        MvcResult mvcResult = mockMvc.perform(put(url, orderId).param("status", orderStatus.name()))
                .andExpect(status().isOk())
                .andReturn();

        verify(orderService).setOrderStatus(orderId, orderStatus);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(order1);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void createOrderTrackByOrderIdTest() throws Exception {
        int orderId = order1.getId();
        String description = "Processing.";

        OrderTrack orderTrack = new OrderTrack(order1, description);
        when(orderService.createOrderTrack(orderId, description)).thenReturn(orderTrack);

        String url = baseUrl + "/{orderId}/tracks";
        MvcResult mvcResult = mockMvc.perform(post(url, orderId).param("description", description))
                .andExpect(status().isCreated())
                .andReturn();

        verify(orderService).createOrderTrack(orderId, description);

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(orderTrack);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }
}
