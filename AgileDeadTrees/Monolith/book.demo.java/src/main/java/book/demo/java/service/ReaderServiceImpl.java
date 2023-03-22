package book.demo.java.service;

import book.demo.java.exception.ReaderNotFoundException;
import book.demo.java.model.Reader;
import book.demo.java.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    @Override
    public Map<String, Object> getReadersWithPaging(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Reader> pageReaders = readerRepository.findAll(paging);
        List<Reader> readers = pageReaders.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("readers", readers);
        response.put("currentPage", pageReaders.getNumber());
        response.put("totalItems", pageReaders.getTotalElements());
        response.put("totalPages", pageReaders.getTotalPages());
        return response;
    }

    @Override
    public Integer createReader(Reader reader) {
        return readerRepository.save(reader).getId();
    }

    @Override
    public Reader getReaderById(int readerId) {
        return readerRepository.findById(readerId)
                .orElseThrow(() -> new ReaderNotFoundException("Reader id " + readerId + " NOT FOUND."));
    }

    @Override
    public void deleteReaderById(int readerId) {
        readerRepository.deleteById(readerId);
    }




}
