/**
 * This is the entity class of an external user called Reader.
 * Reader class extends AbsExternal and includes additional fields such as firstName, lastName, etc.
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
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "readers")
@Data
public class Reader extends AbsExternalUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -7114988145643846309L;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader reader)) return false;
        if (!super.equals(o)) return false;
        return getId() == reader.getId() && Objects.equals(getFirstName(), reader.getFirstName())
                && Objects.equals(getLastName(), reader.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getFirstName(), getLastName());
    }
}
