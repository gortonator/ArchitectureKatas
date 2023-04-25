package book.demo.java.service;

import book.demo.java.entity.account.external.Writer;

public interface WriterService {

    Writer findWriterByUsername(String username);
}
