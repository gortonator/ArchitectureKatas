package book.demo.java.graphql_controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.service.ReaderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;

@Controller
public class ReaderControllers {
    
    private ReaderService readerService;

    public ReaderControllers (ReaderService readerService) {
        this.readerService = readerService;
    }

    @QueryMapping
    public List<Reader> getAllReaders() {
        return readerService.getAllReaders();
    }

    @QueryMapping
    public Page<Reader> getReadersWithPaging(@Argument Integer page, @Argument Integer size) {
        return readerService.getReadersWithPaging(page, size);
    }

    @QueryMapping
    public Reader getReaderById(@Argument Integer id) {
        return readerService.getReaderById(id);
    }

    @QueryMapping
    public Reader findReaderByUsername(@Argument String username) {
        return readerService.findReaderByUsername(username);
    }

    @MutationMapping
    public void deleteReaderById(@Argument Integer id) {
        readerService.deleteReaderById(id);
   }

    
}
