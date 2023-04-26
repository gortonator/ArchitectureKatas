package book.demo.java.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_tracks")
@Data
public class OrderTrack implements Serializable {
    @Serial
    private static final long serialVersionUID = 9153795308821072167L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_track_id")
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(length = 256, nullable = false)
    private String details;

    @Column(name = "updated_time")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public OrderTrack() {
    }

    public OrderTrack(Order order, String details) {
        this.order = order;
        this.details = details;
    }
}
