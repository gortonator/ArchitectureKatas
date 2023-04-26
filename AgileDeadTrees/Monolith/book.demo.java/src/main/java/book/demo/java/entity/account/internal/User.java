package book.demo.java.entity.account.internal;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -444404252404234799L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    protected int id;

    @Size(min = 5)
    @Column(nullable = false, unique = true)
    protected String username;

    @Column(length = 64, nullable = false)
    protected String password;

    @Email
//    @Column(unique = true)
    protected String email;

    @Size(min = 2)
    @Column(name = "first_name", length = 50)
    protected String firstName;

    @Size(min = 2)
    @Column(name = "last_name", length = 50)
    protected String lastName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void removeRoles() {
//        Iterator<Role> iterator = this.roles.iterator();
//
//        while (iterator.hasNext()) {
//            Role role = iterator.next();
//            role.getUsers().remove(this);
//            iterator.remove();
//        }
        this.roles.clear();
    }

    public boolean hasRole(String roleName) {
        for (Role role : this.roles) {
            if (role.getName().equals(roleName)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((User) obj).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getEmail(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
