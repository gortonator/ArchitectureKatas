package book.demo.java.entity.book;

import book.demo.java.entity.book.editorial.DraftBook;
import book.demo.java.entity.book.published.PublishedBook;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
public class Author implements Serializable {

    @Serial
    private static final long serialVersionUID = 844048742979303914L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int id;

    @Size(min = 2)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(min = 2)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "authors_published_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "published_book_id")
    )
    private Set<PublishedBook> publishedBooks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "authors_draft_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "draft_book_id")
    )
    private Set<DraftBook> draftBooks = new HashSet<>();

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void addPublishedBook(PublishedBook book) {
        this.publishedBooks.add(book);
        book.getAuthors().add(this);
    }

    public void removePublishedBook(PublishedBook book) {
        this.publishedBooks.remove(book);
        book.getAuthors().remove(this);
    }

    public void removePublishedBooks() {
        Iterator<PublishedBook> iterator = this.publishedBooks.iterator();

        while (iterator.hasNext()) {
            PublishedBook book = iterator.next();

            book.getAuthors().remove(this);
            iterator.remove();
        }
    }

    public void addDraftBook(DraftBook book) {
        this.draftBooks.add(book);
        book.getAuthors().add(this);
    }

    public void removeDraftBook(DraftBook book) {
        this.draftBooks.remove(book);
        book.getAuthors().remove(this);
    }

    public void removeDraftBooks() {
        Iterator<DraftBook> iterator = this.draftBooks.iterator();

        while (iterator.hasNext()) {
            DraftBook book = iterator.next();

            book.getAuthors().remove(this);
            iterator.remove();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return getId() == author.getId() && Objects.equals(getFirstName(), author.getFirstName())
                && Objects.equals(getLastName(), author.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
