/**
 * This is the entity class representing a published book in the publication company. Each published book
 * could have multiple book variants (in different book format with corresponding prices). A published book
 * could be associated with multiple Authors.
 *
 * @author Tong
 */

package book.demo.java.entity.book.published;

import book.demo.java.entity.book.AbsBook;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "published_books")
@Data
public class PublishedBook extends AbsBook implements Serializable {
    @Serial
    private static final long serialVersionUID = 3468877967393125144L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "published_book_id")
    private int id;

    @ManyToMany(mappedBy = "publishedBooks", fetch = FetchType.LAZY)
    @Column(name = "authors")
    @JsonIgnore
    private Set<Author> authors;

    @JsonManagedReference
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookVariant> bookVariants = new ArrayList<>();

    public PublishedBook() {
    }

    public PublishedBook(String title, Genre genre, Set<Author> authors) {
        super(title, genre);
//        if (!authors.isEmpty()) {
//            addPublishedBookToAuthors(authors);
//        }
    }

//    private void addPublishedBookToAuthors(Set<Author> authors) {
//        this.authors = authors;
//        for (Author author : this.authors) {
//            author.addPublishedBook(this);
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublishedBook that)) return false;
        if (!super.equals(o)) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

    @Override
    public String toString() {
        return "PublishedBook{" +
                "id=" + id +
                ", authors=" + authors +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                '}';
    }
}
