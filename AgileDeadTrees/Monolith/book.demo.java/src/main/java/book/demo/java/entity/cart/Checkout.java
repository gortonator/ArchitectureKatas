package book.demo.java.entity.cart;

import book.demo.java.entity.book.published.BookFormat;

import java.math.BigDecimal;
import java.util.List;

public class Checkout {

    private final BigDecimal MINIMUM_TOTAL_PAYMENT = BigDecimal.valueOf(35);

    private List<CartItem> cartItems;

    private PaymentType paymentType = PaymentType.CARD;

    private ShippingType shippingType = ShippingType.STANDARD;

    private BigDecimal subtotal;

    private BigDecimal shippingFee;

    private int numOfItems;

    private BigDecimal grandTotal;

    private int deliverDays = ShippingType.STANDARD.getDeliverDays();

    public Checkout(List<CartItem> cartItems) {
        generateSummary(cartItems);
    }

    private void generateSummary(List<CartItem> cartItems) {
        this.cartItems = cartItems;

        if (isAllEbook()) this.shippingType = ShippingType.NONE;

        setNumOfItems(calculateNumOfItems());
        setSubtotal(calculateSubTotal());
        setShippingFee(calculateShippingFee());
        setGrandTotal(calculateGrandTotal());
    }

    public Checkout updateShippingType(ShippingType shippingType) {
        if ((!isAllEbook() && shippingType == ShippingType.NONE)
                || isAllEbook() && shippingType != ShippingType.NONE) {
            return this;
        }
        setShippingType(shippingType);
        setDeliverDays(shippingType.getDeliverDays());
        setShippingFee(calculateShippingFee());
        setGrandTotal(calculateGrandTotal());
        return this;
    }

    public boolean isAllEbook() {
        return this.cartItems.stream()
                .allMatch(item -> item.getBookVariant().getFormat() == BookFormat.EBOOK);
    }

    public Checkout updatePaymentType(PaymentType paymentType) {
        setPaymentType(paymentType);
        return this;
    }

    private BigDecimal calculateSubTotal() {
        return cartItems.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateGrandTotal() {
        if ((subtotal != null) && (shippingFee != null)) return subtotal.add(shippingFee);
        return subtotal == null? null : subtotal;
    }

    private int calculateNumOfItems() {
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

    private BigDecimal calculateShippingFee() {
        if (shippingType == ShippingType.STANDARD) {
            if (subtotal.compareTo(MINIMUM_TOTAL_PAYMENT) >= 0) {
                return BigDecimal.ZERO;
            } else {
                return ShippingType.STANDARD.getShippingFee();
            }
        }
        return shippingType.getShippingFee();
    }

    private void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    private void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    private void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    private void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    private void setShippingType(ShippingType shippingType) {
        this.shippingType = shippingType;
    }

    private void setDeliverDays(int deliverDays) {
        this.deliverDays = deliverDays;
    }

    private void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = calculateGrandTotal();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public ShippingType getShippingType() {
        return shippingType;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public int getDeliverDays() {
        return deliverDays;
    }

}

