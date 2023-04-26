package book.demo.java.entity.book.editorial;

import book.demo.java.entity.account.internal.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reviews")
@Data
public class Review implements Serializable {

    @Serial
    private static final long serialVersionUID = -5335781463397071664L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "draft_chapter_id")
    @JsonBackReference
    private DraftChapter draftChapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User editor;

    private String content;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EditorComment> editorComments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WriterComment> writerComments = new ArrayList<>();

    public Review() {
    }

    public Review(DraftChapter draftChapter, User editor, String content) {
        this.draftChapter = draftChapter;
        this.editor = editor;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return getId() == review.getId() && Objects.equals(getContent(), review.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent());
    }
}
