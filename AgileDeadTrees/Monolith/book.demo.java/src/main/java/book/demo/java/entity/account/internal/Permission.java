package book.demo.java.entity.account.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = -3576648057675469269L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private int id;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    @Column(length = 150, nullable = false)
    private String description;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    public Permission() {
    }

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((Permission) obj).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


