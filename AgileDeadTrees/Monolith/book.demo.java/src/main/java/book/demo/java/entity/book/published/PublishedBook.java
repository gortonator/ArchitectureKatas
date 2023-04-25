package book.demo.java.entity.book.published;


import book.demo.java.entity.book.AbstractBook;
import book.demo.java.entity.book.Author;
import book.demo.java.entity.book.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "published_books")
@Data
public class PublishedBook extends AbstractBook implements Serializable {
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
        if (!authors.isEmpty()) {
            addPublishedBookToAuthors(authors);
        }
    }

    private void addPublishedBookToAuthors(Set<Author> authors) {
        this.authors = authors;
        for (Author author : this.authors) {
            author.addPublishedBook(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (getClass() != obj.getClass()) return false;

        return id == ((PublishedBook) obj).getId();
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
