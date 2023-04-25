package book.demo.java.controller;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.service.ReaderService;
import book.demo.java.util.PredefinedRole;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;


    @Operation(summary = "Get all readers.")
    @GetMapping("/all")
    @RequiresRoles(PredefinedRole.ADMIN_ROLE)
    public ResponseEntity<List<Reader>> getAllReaders() {
        List<Reader> readers = readerService.getAllReaders();

        if (readers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(readers, HttpStatus.OK);
    }

    @Operation(summary = "Get all readers with pagination.")
    @GetMapping
    public ResponseEntity<Page<Reader>> getReadersWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Reader> pageReaders = readerService.getReadersWithPaging(page, size);
        if (!pageReaders.hasContent()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageReaders, HttpStatus.OK);
    }

    @Operation(summary = "Get a reader by reader id.")
    @GetMapping("/{readerId}")
    public ResponseEntity<Reader> getReaderById(@PathVariable int readerId) {
        Reader reader = readerService.getReaderById(readerId);
        return new ResponseEntity<>(reader, HttpStatus.OK);
    }

    @Operation(summary = "Delete a reader by reader id.")
    @DeleteMapping("/{readerId}")
    public ResponseEntity<HttpStatus> deleteReaderById(@PathVariable int readerId) {
        readerService.deleteReaderById(readerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
