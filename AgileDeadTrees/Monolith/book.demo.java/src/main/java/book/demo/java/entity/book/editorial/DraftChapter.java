package book.demo.java.entity.book.editorial;

import book.demo.java.entity.book.Author;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "draft_chapters")
@Data
public class DraftChapter extends AbsChapter implements Serializable {

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
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((DraftChapter) obj).getId();
    }
}
