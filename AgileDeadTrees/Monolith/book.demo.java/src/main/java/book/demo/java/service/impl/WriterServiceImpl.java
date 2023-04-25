package book.demo.java.service.impl;

import book.demo.java.entity.account.external.Writer;
import book.demo.java.repository.WriterRepository;
import book.demo.java.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WriterServiceImpl implements WriterService {

    @Autowired
    private WriterRepository writerRepository;

    @Override
    public Writer findWriterByUsername(String username) {
        return writerRepository.findByUsername(username);
    }
}
