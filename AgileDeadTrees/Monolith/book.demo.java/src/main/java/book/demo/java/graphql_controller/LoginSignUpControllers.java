package book.demo.java.graphql_controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import book.demo.java.dto.RegInfo;
import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;
import book.demo.java.service.AuthService;

@Controller
public class LoginSignUpControllers {
    private AuthService authService;

    public LoginSignUpControllers(AuthService authService) {
        this.authService = authService;
    }

    @MutationMapping
    public void userLogin(@Argument String username, @Argument String password) {
        authService.userLogin(username,password);
    }

    @MutationMapping
    public void readerLogin(@Argument String username, @Argument String password) {
        authService.readerLogin(username,password);
    }

    @MutationMapping
    public void writerLogin(@Argument String username, @Argument String password) {
        authService.writerLogin(username,password);
    }

    @MutationMapping
    public void logout() {
        authService.logout();
    }

    @MutationMapping
    public RegInfo registerUser(@Argument User user) {
        return authService.registerUser(user);
    }

    @MutationMapping
    public RegInfo registerReader(@Argument Reader reader) {
        return authService.registerReader(reader);
    }

    @MutationMapping
    public RegInfo registerWriter(@Argument Writer writer, @Argument Integer authorId) {
        return authService.registerWriter(writer, authorId);
    }

}
