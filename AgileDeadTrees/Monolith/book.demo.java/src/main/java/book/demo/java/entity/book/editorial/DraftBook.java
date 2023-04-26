package book.demo.java.entity.book.editorial;

import book.demo.java.entity.book.AbstractBook;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "draft_books")
@Data
public class DraftBook extends AbstractBook implements Serializable {

    @Serial
    private static final long serialVersionUID = 7201764923996149373L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "draft_book_id")
    private int id;

    @ManyToMany(mappedBy = "draftBooks", fetch = FetchType.LAZY)
    @Column(name = "authors")
    @JsonIgnore
    private Set<Author> authors = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "draftBook", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "accepted_chapters")
    @JsonIgnore
    private List<AcceptedChapter> acceptedChapters = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "draftBook", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "draft_chapters")
    @JsonIgnore
    private List<DraftChapter> draftChapters = new ArrayList<>();

    public DraftBook() {
    }

    public DraftBook(String title, Genre genre, Set<Author> authors) {
        super(title, genre);
        if (!authors.isEmpty()) {
            addDraftBookToAuthors(authors);
        }
    }

    private void addDraftBookToAuthors(Set<Author> authors) {
        this.authors = authors;
        for (Author author : this.authors) {
            author.addDraftBook(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DraftBook draftBook)) return false;
        return getId() == draftBook.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

    @Override
    public String toString() {
        return "DraftBook{" +
                "id=" + id +
                ", authors=" + authors +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                '}';
    }
}
