package book.demo.java.entity.account.external;

import book.demo.java.entity.book.Author;
import book.demo.java.util.PredefinedRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "writers")
@Data
public class Writer extends AbsExternalUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writer_id")
    private int id;

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
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((Writer) obj).getId();
    }
}
