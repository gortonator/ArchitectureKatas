/**
 * This class represents an Order entity. A Reader could have multiple orders placed. Each order contains multiple
 * order details to represent entries in order. Mostly the properties of an order are set by a Checkout object.
 *
 * @author Tong
 */

package book.demo.java.entity.order;

import book.demo.java.entity.account.Address;
import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.cart.PaymentType;
import book.demo.java.entity.cart.ShippingType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = -4074703792569304048L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "order_details", nullable = false)
    private List<OrderDetail> orderDetails;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    @JsonBackReference
    private Reader reader;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @JsonBackReference
    private Address address;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "datetime_created", nullable = false)
    private LocalDateTime datetimeCreated = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.PAID;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updated_time ASC")
    private List<OrderTrack> orderTracks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_type", nullable = false)
    private ShippingType shippingType;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal shippingFee;

    @Column(name = "num_items", nullable = false)
    private int numOfItems;

    @Column(name = "grand_total", nullable = false)
    private BigDecimal grandTotal;

    @Column(name = "deliver_days", nullable = false)
    private int deliverDays;

    @Column(name = "expected_deliver_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedDeliverDate;

    protected Order() {
    }

    public Order(Reader reader) {
        this.reader = reader;
    }

}
