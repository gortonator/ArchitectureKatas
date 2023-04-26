package book.demo.java.entity.book.editorial;

import book.demo.java.entity.book.Author;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "accepted_chapters", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"draft_book_id", "chapter_index"})
})
@Data
public class AcceptedChapter extends AbsChapter implements Serializable {

    @Serial
    private static final long serialVersionUID = 4267070392362955713L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accepted_chapter_id")
    private int id;

    public AcceptedChapter() {
    }

    public AcceptedChapter(Integer chapterIndex, String title, String content, DraftBook draftBook, Author author) {
        super(chapterIndex, title, content, draftBook, author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcceptedChapter that)) return false;
        if (!super.equals(o)) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
