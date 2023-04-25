/**
 * This is the entity class of an external user called Reader.
 * Readers class extends AbsExternal and includes additional fields such as firstName, lastName, etc.
 *
 * @author Tong
 */

package book.demo.java.entity.account.external;

import book.demo.java.entity.account.Address;
import book.demo.java.entity.order.Order;
import book.demo.java.util.PredefinedRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "readers")
@Data
public class Reader extends AbsExternalUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private int id;

    @Size(min = 2)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(min = 2)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @OneToOne(mappedBy = "reader", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "reader", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Order> orderList = new ArrayList<>();

    public Reader() {
    }

    public Reader(String username, String password, String email) {
        super(username, password, email);
    }

    public Reader(String firstName, String lastName, String username, String password, String email) {
        super(username, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getRole() {
        return PredefinedRole.READER_ROLE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((Reader) obj).getId();
    }
}
