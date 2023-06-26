package book.demo.java.graphql_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.cart.CartItem;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.cart.PaymentType;
import book.demo.java.entity.cart.ShippingType;
import book.demo.java.service.CartItemService;
import book.demo.java.service.CheckoutService;
import book.demo.java.service.EmailService;
import book.demo.java.service.OrderService;
import book.demo.java.service.ReaderService;
import book.demo.java.util.AuthUtil;
import book.demo.java.util.EmailDetails;
import book.demo.java.entity.order.Order;

@Controller
public class CheckoutControllers {
    @Autowired
    private CartItemService cartItemService;
    
    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReaderService readerService;


    public CheckoutControllers(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @QueryMapping
    public Checkout getCheckout() {
        String username = AuthUtil.getAuthenticatedUsername();
        List<CartItem> cartItems = cartItemService.getCartItemsByReaderUsername(username);
        return checkoutService.getCheckout(cartItems);
    }

    @MutationMapping
    public void updateShippingType(@Argument Checkout checkout, @Argument ShippingType shippingType) {
        checkoutService.updateShippingType(checkout, shippingType);
    }

    @MutationMapping
    public void updatePaymentType(@Argument Checkout checkout, @Argument PaymentType paymentType) {
        checkoutService.updatePaymentType(checkout, paymentType);
    }

    @MutationMapping
    public Order placeOrder() {
        String username = AuthUtil.getAuthenticatedUsername();
        Reader reader = readerService.findReaderByUsername(username);

        List<CartItem> cartItems = cartItemService.getCartItemsByReaderUsername(username);
        Checkout checkout = checkoutService.getCheckout(cartItems);
        Order order = orderService.createOrder(reader, checkout);
        return order;
    }

}