package book.demo.java.service.impl;

import book.demo.java.dto.RegInfo;
import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;
import book.demo.java.entity.book.Author;
import book.demo.java.repository.AuthorRepository;
import book.demo.java.repository.ReaderRepository;
import book.demo.java.repository.UserRepository;
import book.demo.java.repository.WriterRepository;
import book.demo.java.security.LoginType;
import book.demo.java.security.UsernamePasswordLoginTypeToken;
import book.demo.java.service.AuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ReaderRepository readerRepo;

    @Autowired
    private WriterRepository writerRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Override
    public void userLogin(String username, String password) {
        executeLogin(username, password, LoginType.USER);
    }

    @Override
    public void readerLogin(String username, String password) {
        executeLogin(username, password, LoginType.READER);
    }

    @Override
    public void writerLogin(String username, String password) {
        executeLogin(username, password, LoginType.WRITER);
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    @Override
    public RegInfo registerUser(User user) {
        String encryptedPassword = getEncryptedPassword(user.getPassword(), user.getUsername());
        user.setPassword(encryptedPassword);
        User regUser = userRepo.save(user);
        return new RegInfo(regUser.getFirstName(), regUser.getLastName(), regUser.getUsername(), regUser.getEmail());
    }

    @Override
    public RegInfo registerReader(Reader reader) {
        String encryptedPassword = getEncryptedPassword(reader.getPassword(), reader.getUsername());
        reader.setPassword(encryptedPassword);
        Reader regReader = readerRepo.save(reader);
        return new RegInfo(regReader.getFirstName(), regReader.getLastName(), regReader.getUsername(), regReader.getEmail());
    }

    @Override
    public RegInfo registerWriter(Writer writer, int authorId) {
        Author author = authorRepo.findById(authorId).orElseThrow(() -> new NoSuchElementException("Author with id " + authorId + " not found."));
        writer.setAuthor(author);
        String encryptedPassword = getEncryptedPassword(writer.getPassword(), writer.getUsername());
        writer.setPassword(encryptedPassword);
        Writer regWriter = writerRepo.save(writer);

        return new RegInfo(author.getFirstName(), author.getLastName(), regWriter.getUsername(), regWriter.getEmail());
    }

    private void executeLogin(String username, String password, LoginType loginType) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordLoginTypeToken token = new UsernamePasswordLoginTypeToken(username, password, loginType);
        subject.login(token);
        if (!subject.isAuthenticated()) token.clear();
    }

    // username is used as salt for encryption
    private String getEncryptedPassword(String inputPassword, String inputUsername) {
        return new SimpleHash("MD5", inputPassword, inputUsername, 1024).toString();
    }

}
