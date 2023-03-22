package book.demo.java.service;

import book.demo.java.model.*;
import book.demo.java.repository.BookRepository;
import book.demo.java.repository.CartItemRepository;
import book.demo.java.repository.OrderRepository;
import book.demo.java.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private ReaderRepository readerRepo;

    @Override
    public List<Order> getOrdersByReaderID(int readerId) {
        return orderRepo.findByReaderId(readerId);
    }

    @Override
    public Order getOrder(int orderId) {
        // to be revised
        return orderRepo.findById(orderId).get();
    }

    @Override
    public Order createOrder(int readerId) {
        // to be revised
        Reader reader = readerRepo.findById(readerId).get();
        Order order = new Order(reader);
        List<OrderDetail> orderDetails = order.getOrderDetails();

        List<CartItem> cartItemList = cartRepo.findByReaderId(readerId);
        for (CartItem cartItem: cartItemList) {
            OrderDetail orderDetail = new OrderDetail(
                    cartItem.getBook(),
                    order,
                    cartItem.getQuantity());
            orderDetails.add(orderDetail);
        }

        return orderRepo.save(order);
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepo.deleteById(orderId);
    }
}
