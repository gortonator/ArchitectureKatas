package book.demo.java.service;

import book.demo.java.dto.RegInfo;
import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;

public interface AuthService {

    void userLogin(String username, String password);

    void readerLogin(String username, String password);

    void writerLogin(String username, String password);

    void logout();

    RegInfo registerUser(User user);

    RegInfo registerReader(Reader reader);

    RegInfo registerWriter(Writer writer, int authorId);

}
