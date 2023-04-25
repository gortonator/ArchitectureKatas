package book.demo.java.service.impl;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.cart.CartItem;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.order.Order;
import book.demo.java.entity.order.OrderDetail;
import book.demo.java.entity.order.OrderStatus;
import book.demo.java.entity.order.OrderTrack;
import book.demo.java.repository.CartItemRepository;
import book.demo.java.repository.OrderRepository;
import book.demo.java.repository.OrderTrackRepository;
import book.demo.java.repository.ReaderRepository;
import book.demo.java.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private ReaderRepository readerRepo;

    @Autowired
    private OrderTrackRepository orderTrackRepo;


    @Override
    public List<Order> getOrdersByReaderID(int readerId) {
        return orderRepo.findByReaderId(readerId);
    }

    @Override
    public Order getOrder(int orderId) {
        return orderRepo.findById(orderId).orElseThrow(() ->
                new NoSuchElementException("Order id " + orderId + " NOT FOUND."));
    }

    @Override
    public Order createOrder(Reader reader, Checkout checkout) {
        Order order = new Order(reader);
        if (reader.getAddress() == null) {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        order.setAddress(reader.getAddress());
        setOrderFromCheckout(order, checkout);
        Order createdOrder = orderRepo.save(order);
        cartRepo.deleteAll(checkout.getCartItems());
        return createdOrder;
    }

    @Override
    public Order setOrderStatus(int orderId, OrderStatus status) {
        Order order = getOrder(orderId);
        order.setOrderStatus(status);
        return orderRepo.save(order);
    }

    @Override
    public OrderTrack createOrderTrack(int orderId, String description) {
        Order order = getOrder(orderId);
        OrderTrack orderTrack = new OrderTrack(order, description);
        return orderTrackRepo.save(orderTrack);
    }

    private void setOrderFromCheckout(Order order, Checkout checkout) {
        List<OrderDetail> orderDetails = createOrderDetails(order, checkout.getCartItems());
        order.setOrderDetails(orderDetails);
        order.setPaymentType(checkout.getPaymentType());
        order.setShippingType(checkout.getShippingType());
        order.setShippingFee(checkout.getShippingFee());
        order.setSubtotal(checkout.getSubtotal());
        order.setNumOfItems(checkout.getNumOfItems());
        order.setGrandTotal(checkout.getGrandTotal());
        order.setDeliverDays(checkout.getDeliverDays());
        order.setExpectedDeliverDate(order.getDatetimeCreated().toLocalDate().plusDays(checkout.getDeliverDays()));
    }

    private List<OrderDetail> createOrderDetails(Order order, List<CartItem> cartItems) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail(
                    cartItem.getBookVariant(),
                    order,
                    cartItem.getQuantity());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepo.deleteById(orderId);
    }
}
