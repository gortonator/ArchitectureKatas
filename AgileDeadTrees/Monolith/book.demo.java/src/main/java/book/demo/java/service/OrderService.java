package book.demo.java.service;

import book.demo.java.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrdersByReaderID(int readerId);

    Order getOrder(int orderId);

    Order createOrder(int readerId);

    void deleteOrder(int orderId);
}
