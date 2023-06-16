package book.demo.java.graphql_controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import book.demo.java.entity.cart.CartItem;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.cart.PaymentType;
import book.demo.java.entity.cart.ShippingType;
import book.demo.java.service.CheckoutService;

@Controller
public class CheckoutControllers {
    private CheckoutService checkoutService;

    public CheckoutControllers(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @QueryMapping
    public Checkout getCheckout(@Argument List<CartItem> cartItems) {
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

}
