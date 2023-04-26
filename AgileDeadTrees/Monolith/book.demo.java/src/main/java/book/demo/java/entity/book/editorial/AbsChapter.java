package book.demo.java.entity.book.editorial;

import book.demo.java.entity.book.Author;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@Data
public abstract class AbsChapter {

    @Column(name = "chapter_index")
    protected Integer chapterIndex;

    @Column(nullable = false)
    protected String title;

    @Column(nullable = false)
//    @Lob
    protected String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "draft_book_id", nullable = false)
    @JsonBackReference
    protected DraftBook draftBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference
    protected Author author;

    public AbsChapter() {
    }

    public AbsChapter(Integer chapterIndex, String title, String content) {
        this.chapterIndex = chapterIndex;
        this.title = title;
        this.content = content;
    }

    public AbsChapter(Integer chapterIndex, String title, String content, DraftBook draftBook, Author author) {
        this.chapterIndex = chapterIndex;
        this.title = title;
        this.content = content;
        this.draftBook = draftBook;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbsChapter that)) return false;
        return getChapterIndex().equals(that.getChapterIndex()) && getTitle().equals(that.getTitle()) && getContent().equals(that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChapterIndex(), getTitle(), getContent());
    }
}
