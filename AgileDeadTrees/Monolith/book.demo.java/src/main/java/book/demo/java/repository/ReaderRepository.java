package book.demo.java.repository;

import book.demo.java.entity.account.external.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Integer> {

    Page<Reader> findAll(Pageable pageable);

    Reader findByUsername(String username);
}
