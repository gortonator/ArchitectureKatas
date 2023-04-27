package book.demo.java.service.impl;

import book.demo.java.entity.cart.CartItem;
import book.demo.java.entity.cart.Checkout;
import book.demo.java.entity.cart.PaymentType;
import book.demo.java.entity.cart.ShippingType;
import book.demo.java.service.CheckoutService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {


    @Override
    public Checkout getCheckout(List<CartItem> cartItems) {
        return new Checkout(cartItems);
    }

    @Override
    public void updateShippingType(Checkout checkout, ShippingType shippingType) {
        if (!checkout.isAllEbook() && shippingType == ShippingType.NONE) {
            throw new IllegalArgumentException("There are physical books in cart therefore shipping fee is required.");
        }

        if (checkout.isAllEbook() && shippingType != ShippingType.NONE) {
            throw new IllegalArgumentException("There are no physical books in cart " + "therefore shipping fee is not required.");
        }
        checkout.updateShippingType(shippingType);
    }

    @Override
    public void updatePaymentType(Checkout checkout, PaymentType paymentType) {
        checkout.updatePaymentType(paymentType);
    }

}
