/**
 * This is the entity class of an external user called Writer.
 * Writer class extends AbsExternal and each Writer account corresponds to an Author in the database system.
 *
 * @author Tong
 */

package book.demo.java.entity.account.external;

import book.demo.java.entity.book.Author;
import book.demo.java.util.PredefinedRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "writers")
@Data
public class Writer extends AbsExternalUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -3256079418226402989L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writer_id")
    private int id;

    // Each Writer account is associated with a corresponding Author entity in database.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    public Writer() {
    }

    public Writer(String username, String password, String email, Author author) {
        super(username, password, email);
        this.author = author;
    }

    @Override
    public String getRole() {
        return PredefinedRole.WRITER_ROLE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Writer writer)) return false;
        if (!super.equals(o)) return false;
        return getId() == writer.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
