/**
 * This is an entity class representing a comment from an editor. Each comment is associated with a User (with
 * Editor role).
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
@Table(name = "editor_comments")
@Data
public class EditorComment extends AbsComment implements Serializable {

    @Serial
    private static final long serialVersionUID = -8301690689867432966L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "editor_comment_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public EditorComment() {
    }

    public EditorComment(String content, Review review, User user) {
        super(content, review);
        this.user = user;
    }

    @Override
    public CommenterType getCommenterType() {
        return CommenterType.EDITOR;
    }

    @Override
    public String getCommenterUsername() {
        return this.user.getUsername();
    }

    @Override
    public User getEditor() {
        return this.user;
    }

    // Since this is an editor's comment, when getWriter() is called, an exception would be thrown to indicate that
    // there is no writer to get.
    @Override
    @JsonIgnore
    public Writer getWriter() throws InvalidObjectException {
        throw new InvalidObjectException("This is an editor comment, not a writer comment. " +
                "Please use getEditor() instead.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditorComment that)) return false;
        if (!super.equals(o)) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
