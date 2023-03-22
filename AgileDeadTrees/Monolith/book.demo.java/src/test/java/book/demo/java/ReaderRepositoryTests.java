package book.demo.java;

import static org.assertj.core.api.Assertions.assertThat;

import book.demo.java.model.Reader;
import book.demo.java.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReaderRepositoryTests {
    @Autowired
    private ReaderRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewReader() {
        Reader reader = new Reader("Ally", "Dao", "ADaomm", "address");
        Reader savedReader = repo.save(reader);
        assertThat(savedReader.getId()).isGreaterThan(0);
    }
}
