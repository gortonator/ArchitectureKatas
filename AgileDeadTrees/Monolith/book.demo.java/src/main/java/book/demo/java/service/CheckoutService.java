package book.demo.java.service;

import book.demo.java.entity.cart.CartItem;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.cart.PaymentType;
import book.demo.java.entity.cart.ShippingType;

import java.util.List;

public interface CheckoutService {

    Checkout getCheckout(List<CartItem> cartItems);

    void updateShippingType(Checkout checkout, ShippingType shippingType);

    void updatePaymentType(Checkout checkout, PaymentType paymentType);
}
