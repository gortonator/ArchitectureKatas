package book.demo.java.entity.cart;

import java.math.BigDecimal;

public enum ShippingType {
    STANDARD(10, BigDecimal.valueOf(5)),
    EXPRESS(5, BigDecimal.valueOf(15)),
    NONE(0, BigDecimal.ZERO);

    private int deliverDays;

    private BigDecimal shippingFee;

    ShippingType(int deliverDays, BigDecimal shippingFee) {
        this.deliverDays = deliverDays;
        this.shippingFee = shippingFee;
    }

    public int getDeliverDays() {
        return deliverDays;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

}
