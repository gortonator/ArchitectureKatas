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

    @Override
    public int hashCode() {
        return 2021;
    }

    public abstract String getRole();
}
