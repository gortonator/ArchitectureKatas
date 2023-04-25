package book.demo.java.controller;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.cart.CartItem;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.cart.PaymentType;
import book.demo.java.entity.cart.ShippingType;
import book.demo.java.entity.order.Order;
import book.demo.java.service.*;
import book.demo.java.util.AuthUtil;
import book.demo.java.util.EmailDetails;
import book.demo.java.util.PredefinedRole;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final String CHECKOUT_SESSION_ATTR = "checkout";

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReaderService readerService;

    @Operation(summary = "Get Checkout information of logged in reader.")
    @GetMapping
    @RequiresRoles(PredefinedRole.READER_ROLE)
    public ResponseEntity<Checkout> getCheckoutInfo(HttpSession session) {

        String username = AuthUtil.getAuthenticatedUsername();

        List<CartItem> cartItems = cartItemService.getCartItemsByReaderUsername(username);
        if (cartItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Checkout checkout = (Checkout) session.getAttribute(CHECKOUT_SESSION_ATTR);

        // if session is not new, but still no checkout info
        if (session.isNew() || checkout == null) {
            setSessionMaxInactiveInterval(session);
            checkout = checkoutService.getCheckout(cartItems);
            session.setAttribute(CHECKOUT_SESSION_ATTR, checkout);
        }

        if (checkout == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(checkout, HttpStatus.OK);
    }

    @Operation(summary = "Update shipping type of a checkout.")
    @PutMapping("/update/shipping")
    @RequiresRoles(PredefinedRole.READER_ROLE)
    public ResponseEntity<Checkout> updateShippingType(HttpSession session, @RequestParam ShippingType shippingType) {
        Checkout checkout = getCheckoutFromSession(session);
        checkoutService.updateShippingType(checkout, shippingType);
        session.setAttribute(CHECKOUT_SESSION_ATTR, checkout);
        return new ResponseEntity<>(checkout, HttpStatus.OK);
    }

    @Operation(summary = "Update payment type of a checkout.")
    @PutMapping("/update/payment")
    @RequiresRoles(PredefinedRole.READER_ROLE)
    public ResponseEntity<Checkout> updatePaymentType(HttpSession session, @RequestParam PaymentType paymentType) {
        Checkout checkout = getCheckoutFromSession(session);
        checkoutService.updatePaymentType(checkout, paymentType);
        session.setAttribute(CHECKOUT_SESSION_ATTR, checkout);
        return new ResponseEntity<>(checkout, HttpStatus.OK);
    }

    @Operation(summary = "Place an Order.")
    @PostMapping("/place_order")
    @RequiresRoles(PredefinedRole.READER_ROLE)
    public ResponseEntity<Order> saveOrder(HttpSession session) {
        String username = AuthUtil.getAuthenticatedUsername();
        Reader reader = readerService.findReaderByUsername(username);

        Checkout checkout = getCheckoutFromSession(session);
        Order order = orderService.createOrder(reader, checkout);
        EmailDetails emailDetails = new EmailDetails(reader.getEmail(), "Order successfully created.",
                "Order id: " + order.getId() + ".");
        emailService.sendSimpleMail(emailDetails);
        session.removeAttribute(CHECKOUT_SESSION_ATTR);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    private void setSessionMaxInactiveInterval(HttpSession session) {
        int SESSION_MAX_INACTIVE_INTERVAL = 60 * 15;
        session.setMaxInactiveInterval(SESSION_MAX_INACTIVE_INTERVAL);
    }

    private Checkout getCheckoutFromSession(HttpSession session) {
        if (session.isNew()) {
            throw new UnauthenticatedException("Previous session has expired. Please checkout again.");
        }
        Checkout checkout = (Checkout) session.getAttribute(CHECKOUT_SESSION_ATTR);
        if (checkout == null) {
            throw new NoSuchElementException("No checkout for the current session. Please checkout again.");
        }
        return checkout;
    }
}
