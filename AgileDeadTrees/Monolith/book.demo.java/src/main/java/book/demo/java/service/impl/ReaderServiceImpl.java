package book.demo.java.service.impl;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.repository.ReaderRepository;
import book.demo.java.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

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
    public Page<Reader> getReadersWithPaging(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return readerRepository.findAll(paging);
    }

    @Override
    public Reader getReaderById(int readerId) {
        return readerRepository.findById(readerId)
                .orElseThrow(() -> new NoSuchElementException("Reader id " + readerId + " NOT FOUND."));
    }

    @Override
    public Reader findReaderByUsername(String username) {
        return readerRepository.findByUsername(username);
    }

    @Override
    public void deleteReaderById(int readerId) {
        readerRepository.deleteById(readerId);
    }


}
