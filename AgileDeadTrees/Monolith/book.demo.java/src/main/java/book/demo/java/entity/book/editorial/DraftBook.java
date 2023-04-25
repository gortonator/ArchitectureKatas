package book.demo.java.entity.book.editorial;

import book.demo.java.entity.book.AbstractBook;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "draft_books")
@Data
public class DraftBook extends AbstractBook implements Serializable {

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
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((DraftBook) obj).getId();
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
