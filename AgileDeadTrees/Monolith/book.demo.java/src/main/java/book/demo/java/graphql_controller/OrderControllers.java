package book.demo.java.graphql_controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.order.Order;
import book.demo.java.entity.order.OrderStatus;
import book.demo.java.entity.order.OrderTrack;
import book.demo.java.service.OrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;

@Controller
public class OrderControllers {
    private OrderService orderService;

    public OrderControllers(OrderService orderService) {
        this.orderService = orderService;
    }

    @QueryMapping
    public List<Order> getOrdersByReaderID(@Argument Integer id) {
        return orderService.getOrdersByReaderID(id);
    }

    @QueryMapping
    public Order getOrder(@Argument Integer orderId) {
        return orderService.getOrder(orderId);
    }

    // @MutationMapping
    // public Order createOrder(@Argument Reader reader, @Argument Checkout checkout) {
    //     return orderService.createOrder(reader,checkout);
    // }

    @MutationMapping
    public Order setOrderStatus(@Argument Integer orderId, @Argument OrderStatus status) {
        return orderService.setOrderStatus(orderId,status);
    }

    @MutationMapping
    public OrderTrack createOrderTrack(@Argument Integer orderId, @Argument String description) {
        return orderService.createOrderTrack(orderId,description);
    }

    @MutationMapping
    public void deleteOrder(@Argument Integer orderId) {
        orderService.deleteOrder(orderId);
    }
}
