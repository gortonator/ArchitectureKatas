/**
 * This is an entity class representing a comment from a writer. Each comment is associated with a Writer (who is
 * associated with an Author in database).
 *
 * @author Tong
 */

package book.demo.java.entity.book.editorial;

import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.InvalidObjectException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "writer_comments")
@Data
public class WriterComment extends AbsComment implements Serializable {

    @Serial
    private static final long serialVersionUID = 2264356217967809783L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writer_comment_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    @JsonBackReference
    private Writer writer;

    public WriterComment() {
    }

    public WriterComment(String content, Review review, Writer writer) {
        super(content, review);
        this.writer = writer;
    }

    @Override
    public CommenterType getCommenterType() {
        return CommenterType.WRITER;
    }

    // Since this is a writer's comment, when getEditor() is called, an exception would be thrown to indicate that
    // there is no editor to get.
    @Override
    @JsonIgnore
    public User getEditor() throws InvalidObjectException {
        throw new InvalidObjectException("This is an writer comment, not an editor comment. " +
                "Please use getWriter() instead.");
    }

    @Override
    public String getCommenterUsername() {
        return this.writer.getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WriterComment that)) return false;
        if (!super.equals(o)) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
