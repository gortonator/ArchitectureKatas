package book.demo.java.entity.book.editorial;

import book.demo.java.entity.book.Author;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "accepted_chapters", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"draft_book_id", "chapter_index"})
})
@Data
public class AcceptedChapter extends AbsChapter implements Serializable {

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
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((AcceptedChapter) obj).getId();
    }

}
