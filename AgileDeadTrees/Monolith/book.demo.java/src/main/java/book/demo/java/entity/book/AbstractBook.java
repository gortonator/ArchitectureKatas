package book.demo.java.entity.book;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import java.util.Objects;

@MappedSuperclass
@Data
public abstract class AbstractBook {

    @Size(min = 1)
    @Column
    protected String title;

    @Enumerated(EnumType.STRING)
    @Column
    protected Genre genre;

    //    @ConstructorProperties({"book_id", "author", "ISBN", "year", "price"})
    public AbstractBook() {
    }

    public AbstractBook(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBook that)) return false;
        return getTitle().equals(that.getTitle()) && getGenre() == that.getGenre();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getGenre());
    }
}
