package book.demo.java.service;

import book.demo.java.entity.account.external.Reader;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReaderService {

    List<Reader> getAllReaders();

    Page<Reader> getReadersWithPaging(int page, int size);

    Reader getReaderById(int readerId);

    Reader findReaderByUsername(String username);

    void deleteReaderById(int readerId);

}
