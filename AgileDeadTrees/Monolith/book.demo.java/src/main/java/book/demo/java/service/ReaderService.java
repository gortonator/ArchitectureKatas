package book.demo.java.service;

import book.demo.java.model.Reader;

import java.util.List;
import java.util.Map;

public interface ReaderService {

    List<Reader> getAllReaders();

    Map<String, Object> getReadersWithPaging(int page, int size);

    Integer createReader(Reader reader);

    Reader getReaderById(int readerId);

    void deleteReaderById(int readerId);

}
