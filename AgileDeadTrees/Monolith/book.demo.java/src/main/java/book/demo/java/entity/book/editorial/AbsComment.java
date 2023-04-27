/**
 * This is an abstract class that contains some basic common properties of a comment made by Editor or Writer. Each
 * comment is associated with a Review made by editor.
 *
 * @author
 */

package book.demo.java.entity.book.editorial;

import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.InvalidObjectException;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@Data
public abstract class AbsComment {

    protected String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    @JsonBackReference
    protected Review review;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    protected LocalDateTime timestamp = LocalDateTime.now();

    public AbsComment() {
    }

    public AbsComment(String content, Review review) {
        this.content = content;
        this.review = review;
    }

    // get the commenter type of the comment: editor or writer
    public abstract CommenterType getCommenterType();

    // get the username of the commenter
    public abstract String getCommenterUsername();

    @JsonIgnore
    public abstract User getEditor() throws InvalidObjectException;

    @JsonIgnore
    public abstract Writer getWriter() throws InvalidObjectException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbsComment that)) return false;
        return getContent().equals(that.getContent()) && getTimestamp().equals(that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getTimestamp());
    }
}
