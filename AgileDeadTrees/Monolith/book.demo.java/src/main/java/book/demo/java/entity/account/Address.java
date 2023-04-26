package book.demo.java.entity.account;

import book.demo.java.entity.account.external.Reader;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "addresses")
@Data
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 5914372821611983921L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;

    @Column(name = "first_name", nullable = false, length = 45)
    protected String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    protected String lastName;

    @Column(name = "phone_number", nullable = false, length = 15)
    protected String phoneNumber;

    @Column(name = "address_line_1", nullable = false, length = 64)
    protected String addressLine1;

    @Column(name = "address_line_2", length = 64)
    protected String addressLine2;

    @Column(nullable = false, length = 45)
    protected String city;

    @Column(nullable = false, length = 45)
    protected String state;

    @Column(name = "postal_code", nullable = false, length = 10)
    protected String postalCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    @JsonBackReference
    private Reader reader;

    public Address() {
    }

    public Address(String firstName, String lastName, String phoneNumber, String addressLine1, String addressLine2,
                   String city, String state, String postalCode, Reader reader) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.reader = reader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return getId() == address.getId() && getFirstName().equals(address.getFirstName())
                && getLastName().equals(address.getLastName()) && getPhoneNumber().equals(address.getPhoneNumber())
                && getAddressLine1().equals(address.getAddressLine1())
                && Objects.equals(getAddressLine2(), address.getAddressLine2()) && getCity().equals(address.getCity())
                && getState().equals(address.getState()) && getPostalCode().equals(address.getPostalCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNumber(), getAddressLine1(),
                getAddressLine2(), getCity(), getState(), getPostalCode());
    }
}
