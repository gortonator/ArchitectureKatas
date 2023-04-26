package book.demo.java.entity.account.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 4017720062381140477L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(length = 40, nullable = false, unique = true)
    protected String name;

    @Column(length = 150, nullable = false)
    protected String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    public Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
        permission.getRoles().add(this);
    }

    public void removePermission(Permission permission) {
        this.permissions.remove(permission);
        permission.getRoles().remove(this);
    }

    public void removePermissions() {
        Iterator<Permission> iterator = this.permissions.iterator();

        while (iterator.hasNext()) {
            Permission permission = iterator.next();

            permission.getRoles().remove(this);
            iterator.remove();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((Role) obj).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
