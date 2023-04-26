/**
 * This is an abstract class for an external user with common properties.
 *
 * @author Tong
 */

package book.demo.java.entity.account.external;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

@MappedSuperclass
@Data
public abstract class AbsExternalUser {

    @Size(min = 5)
    @Column(length = 64, nullable = false, unique = true)
    protected String username;

    @Column(length = 64, nullable = false)
    protected String password;

    @Email
//    @Column(unique = true)
    protected String email;

    public AbsExternalUser() {
    }

    public AbsExternalUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public abstract String getRole();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbsExternalUser that)) return false;
        return getUsername().equals(that.getUsername())
                && getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }
}
