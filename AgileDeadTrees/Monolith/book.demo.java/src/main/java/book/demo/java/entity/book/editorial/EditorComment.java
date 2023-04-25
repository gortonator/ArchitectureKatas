package book.demo.java.entity.book.editorial;

import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.InvalidObjectException;
import java.io.Serializable;

@Entity
@Table(name = "editor_comments")
@Data
public class EditorComment extends AbsComment implements Serializable {

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

    @Override
    @JsonIgnore
    public Writer getWriter() throws InvalidObjectException {
        throw new InvalidObjectException("This is an editor comment, not a writer comment.");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((EditorComment) obj).getId();
    }

}
