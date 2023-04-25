package book.demo.java.controller;

import book.demo.java.entity.order.Order;
import book.demo.java.entity.order.OrderStatus;
import book.demo.java.entity.order.OrderTrack;
import book.demo.java.service.OrderService;
import book.demo.java.util.PredefinedRole;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get all the orders of a Reader.")
    @GetMapping
    public ResponseEntity<List<Order>> listOrders(@RequestParam int readerId) {
        List<Order> orderList = orderService.getOrdersByReaderID(readerId);
        if (orderList.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @Operation(summary = "Manager sets an order status by Order id.")
    @PutMapping("/{orderId}/status")
    @RequiresRoles(PredefinedRole.MANAGER_ROLE)
    public ResponseEntity<Order> setOrderStatusById(@PathVariable int orderId,
                                                    @RequestParam OrderStatus status) {
        Order order = orderService.setOrderStatus(orderId, status);
        if (order == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "Manager creates order tracks by Order id.")
    @PostMapping("/{orderId}/tracks")
    @RequiresRoles(PredefinedRole.MANAGER_ROLE)
    public ResponseEntity<OrderTrack> createOrderTrackByOrderId(@PathVariable int orderId,
                                                                @RequestParam String description) {
        OrderTrack orderTrack = orderService.createOrderTrack(orderId, description);
        if (orderTrack == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(orderTrack, HttpStatus.CREATED);
    }

    @Operation(summary = "Get an order by Order id.")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable int orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "Delete an order by id.")
    @DeleteMapping("/remove/{orderId}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
