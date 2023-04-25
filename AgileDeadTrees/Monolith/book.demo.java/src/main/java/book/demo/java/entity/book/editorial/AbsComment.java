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

    public abstract CommenterType getCommenterType();

    public abstract String getCommenterUsername();

    @JsonIgnore
    public abstract User getEditor() throws InvalidObjectException;

    @JsonIgnore
    public abstract Writer getWriter() throws InvalidObjectException;

    @Override
    public int hashCode() {
        return 2021;
    }
}
