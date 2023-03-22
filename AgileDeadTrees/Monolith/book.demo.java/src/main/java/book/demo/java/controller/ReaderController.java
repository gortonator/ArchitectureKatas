package book.demo.java.controller;

import book.demo.java.exception.ExceptionUtil;
import book.demo.java.model.Order;
import book.demo.java.model.Reader;
import book.demo.java.service.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Operation(summary = "Get all readers.")
    @GetMapping
    public ResponseEntity<List<Reader>> getAllReaders() {
        try {
            List<Reader> readers =  readerService.getAllReaders();

            if (readers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(readers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all readers with pagination.")
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getReadersWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        try {
            Map<String, Object> response = readerService.getReadersWithPaging(page, size);
            // attention
//            if (readers.isEmpty()) {
//                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //attention
//    @Operation(summary = "Get orders by reader id.")
//    @GetMapping("/{readerId}/orders")
//    public ResponseEntity<List<Order>> getOrdersByReaderId(@PathVariable readerId) {
//
//    }

    @Operation(summary = "Create a new reader.")
    @PostMapping
    public ResponseEntity<Integer> createReader(@Valid @RequestBody Reader reader) {
        try {
            Integer readerId = readerService.createReader(reader);
            return new ResponseEntity<>(readerId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a reader by reader id.")
    @GetMapping("/{readerId}")
    public ResponseEntity<Reader> getReaderById(@PathVariable int readerId) {
        try {
            Reader reader = readerService.getReaderById(readerId);
            return new ResponseEntity<>(reader, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a reader by reader id.")
    @DeleteMapping("/{readerId}")
    public ResponseEntity<HttpStatus> deleteReaderById(@PathVariable int readerId) {
        try {
            readerService.deleteReaderById(readerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
