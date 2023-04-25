package book.demo.java.repository;

import book.demo.java.entity.account.external.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Integer> {

    Writer findByUsername(String username);
}
