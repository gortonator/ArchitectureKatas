package book.demo.java.controller;

import book.demo.java.dto.RegInfo;
import book.demo.java.entity.account.external.Reader;
import book.demo.java.entity.account.external.Writer;
import book.demo.java.entity.account.internal.User;
import book.demo.java.service.AuthService;
import book.demo.java.util.PredefinedRole;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class LoginSignUpController {

    @Autowired
    private AuthService authService;

    /*
     * Below is the Login service section.
     */
    @Operation(summary = "Login API for User.")
    @PostMapping("/user/login")
    public ResponseEntity<String> userLogin(String username, String password) {
        authService.userLogin(username, password);
        String response = "User " + username + " successfully logged in.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Login API for Reader.")
    @PostMapping("/reader/login")
    public ResponseEntity<String> readerLogin(String username, String password) {
        authService.readerLogin(username, password);
        String response = "Reader " + username + " successfully logged in.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Login API for Writer.")
    @PostMapping("/writer/login")
    public ResponseEntity<String> writerLogin(String username, String password) {
        authService.writerLogin(username, password);
        String response = "Writer " + username + " successfully logged in.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Log out page.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        authService.logout();
        String response = "Successfully logged out.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Below is the Registration service section.
     */
    @Operation(summary = "Registration API for User.")
    @PostMapping("/user/register")
    @RequiresRoles(PredefinedRole.ADMIN_ROLE)
    public ResponseEntity<RegInfo> userRegister(@RequestBody User user) {
        RegInfo info = authService.registerUser(user);
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    @Operation(summary = "Registration API for Reader.")
    @PostMapping("/reader/register")
    public ResponseEntity<RegInfo> readerRegister(@RequestBody Reader reader) {
        RegInfo info = authService.registerReader(reader);
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    @Operation(summary = "Registration API for Writer.")
    @PostMapping("/writer/register")
    public ResponseEntity<RegInfo> writerRegister(@RequestBody Writer writer, @RequestParam int authorId) {
        RegInfo info = authService.registerWriter(writer, authorId);
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    /*
    Other endpoints.
     */
    @Operation(summary = "Need to login page.")
    @RequestMapping("/toLogin")
    @ResponseBody
    public String toLogin() {
        return "You are directed to the toLogin page.";
    }

    @Operation(summary = "No authorization page.")
    @RequestMapping("/unauthorized")
    @ResponseBody
    public String noAuth() {
        return "Unauthorized to view.";
    }


}
