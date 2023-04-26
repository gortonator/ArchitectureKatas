package book.demo.java.entity.book.editorial;

import book.demo.java.entity.book.Author;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "draft_chapters")
@Data
public class DraftChapter extends AbsChapter implements Serializable {

    @Serial
    private static final long serialVersionUID = -3528350367957028315L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "draft_chapter_id")
    private int id;

    @Column(name = "pending_content")
//    @Lob
    private String pendingContent;

    @Enumerated(EnumType.STRING)
    private DraftChapterStatus status = DraftChapterStatus.CREATED;

    @OneToOne(mappedBy = "draftChapter", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Review review;

    public DraftChapter() {
    }

    public DraftChapter(Integer chapterIndex, String title, String content, DraftBook draftBook, Author author) {
        super(chapterIndex, title, content, draftBook, author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DraftChapter that)) return false;
        if (!super.equals(o)) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
