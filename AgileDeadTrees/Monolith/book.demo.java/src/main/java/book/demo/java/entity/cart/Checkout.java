/**
 * This is the Checkout class for generating checkout information for Readers before placing an order. It contains
 * properties such as payment type, shipping type, and methods of calculating the breakdowns and total payment amount.
 *
 * @author Tong
 */

package book.demo.java.entity.cart;

import book.demo.java.entity.book.published.BookFormat;

import java.math.BigDecimal;
import java.util.List;

public class Checkout {

    // Minimum grand total is set to be 35. If grand total exceeds 35, then there would be no shipping fee for
    // STANDARD shipping type.
    private final BigDecimal MINIMUM_TOTAL_PAYMENT = BigDecimal.valueOf(35);

    private List<CartItem> cartItems;

    // Default payment type is set to CARD.
    private PaymentType paymentType = PaymentType.CARD;

    // Default shipping type is set to STANDARD.
    private ShippingType shippingType = ShippingType.STANDARD;

    private BigDecimal subtotal;

    private BigDecimal shippingFee;

    private int numOfItems;

    private BigDecimal grandTotal;

    private int deliverDays = ShippingType.STANDARD.getDeliverDays();

    public Checkout(List<CartItem> cartItems) {
        generateSummary(cartItems);
    }

    /*
     * This is a helper method for calculating and setting properties for the Checkout class after initialization.
     */
    private void generateSummary(List<CartItem> cartItems) {
        this.cartItems = cartItems;

        // If all the books in cart are ebooks, then shipping type would be set to None instead of the default
        // "standard" type.
        if (isAllEbook()) this.shippingType = ShippingType.NONE;

        // Calculates other fees accordingly.
        setNumOfItems(calculateNumOfItems());
        setSubtotal(calculateSubTotal());
        setShippingFee(calculateShippingFee());
        setGrandTotal(calculateGrandTotal());
    }

    /*
     * This method updates the shipping type of the checkout. If all book variants are of the form of Ebook, then
     * shipping type should stay as "None".
     */
    public void updateShippingType(ShippingType shippingType) {
        // If not all book variants are ebooks, and the incoming shipping type for update is "NONE", or
        // all book variants are ebooks, and the incoming shipping type for update is not "NONE",
        // then this would be considered as an invalid update request, and no update would be made.
        if ((!isAllEbook() && shippingType == ShippingType.NONE)
                || isAllEbook() && shippingType != ShippingType.NONE) {
            return;
        }

        // When shipping type is change, the delivery days, shipping fee, and grand total would
        // be recalculated correspondingly.
        setShippingType(shippingType);
        setDeliverDays(shippingType.getDeliverDays());
        setShippingFee(calculateShippingFee());
        setGrandTotal(calculateGrandTotal());
    }

    /*
     * This helper method checks whether all the book variants in cart are of the form of EBOOK.
     */
    public boolean isAllEbook() {
        return this.cartItems.stream()
                .allMatch(item -> item.getBookVariant().getFormat() == BookFormat.EBOOK);
    }

    public void updatePaymentType(PaymentType paymentType) {
        setPaymentType(paymentType);
    }

    private BigDecimal calculateSubTotal() {
        return cartItems.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateGrandTotal() {
        if ((subtotal != null) && (shippingFee != null)) return subtotal.add(shippingFee);
        return subtotal == null ? null : subtotal;
    }

    private int calculateNumOfItems() {
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

    /*
     * This is a helper method for calculating shipping fee.
     * To specify, if it's a standard shipping type and grand total does not exceed the minimum grand total limit,
     * then there would be shipping fee accordingly.
     * Otherwise, the shipping fee would correspond to the specific shipping type (express or none).
     */
    private BigDecimal calculateShippingFee() {
        if (shippingType == ShippingType.STANDARD
                && subtotal.compareTo(MINIMUM_TOTAL_PAYMENT) >= 0) {
            return BigDecimal.ZERO;
        }
        return shippingType.getShippingFee();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    private void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ShippingType getShippingType() {
        return shippingType;
    }

    private void setShippingType(ShippingType shippingType) {
        this.shippingType = shippingType;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    private void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    private void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    private void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    private void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = calculateGrandTotal();
    }

    public int getDeliverDays() {
        return deliverDays;
    }

    private void setDeliverDays(int deliverDays) {
        this.deliverDays = deliverDays;
    }

}

