package book.demo.java.service;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.order.Order;
import book.demo.java.entity.order.OrderStatus;
import book.demo.java.entity.order.OrderTrack;

import java.util.List;

public interface OrderService {

    List<Order> getOrdersByReaderID(int readerId);

    Order getOrder(int orderId);

    Order createOrder(Reader reader, Checkout checkout);

    Order setOrderStatus(int orderId, OrderStatus status);

    OrderTrack createOrderTrack(int orderId, String description);

    void deleteOrder(int orderId);
}
