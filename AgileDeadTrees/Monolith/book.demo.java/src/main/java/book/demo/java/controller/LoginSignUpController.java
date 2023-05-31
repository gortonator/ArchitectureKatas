/**
 * This is the controller class for handling HTTP requests related to subject authentication, including operations like
 * logging in, logging out, and signing up.
 * <p>
 * Endpoints:
 * POST /api/user/login: User login.
 * POST /api/reader/login: Reader login.
 * POST /api/writer/login: Writer login.
 * POST /api/logout: logout.
 * POST /api/user/register: Register a User.
 * POST /api/reader/register: Register a Reader.
 * POST /api/writer/register: Register a Writer.
 * GET /api/toLogin: directed page when authentication is needed.
 * GET /api/unauthorized: directed page when subject is unauthorized for a certain endpoint.
 *
 * @author Tong
 */

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

    /**
     * This endpoint provides login functionalities of User login.
     *
     * @param username the username of the User.
     * @param password the password of the User.
     * @return A ResponseEntity indicating whether authentication is successful or not and an HTTP status code.
     */
    @Operation(summary = "Login API for User.")
    @PostMapping("/user/login")
    public ResponseEntity<String> userLogin(String username, String password) {
        authService.userLogin(username, password);
        String response = "User " + username + " successfully logged in.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This endpoint provides login functionalities of Reader login.
     *
     * @param username the username of the Reader.
     * @param password the password of the Reader.
     * @return A ResponseEntity indicating whether authentication is successful or not and an HTTP status code.
     */
    @Operation(summary = "Login API for Reader.")
    @PostMapping("/reader/login")
    public ResponseEntity<String> readerLogin(String username, String password) {
        authService.readerLogin(username, password);
        String response = "Reader " + username + " successfully logged in.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This endpoint provides login functionalities of Writer login.
     *
     * @param username the username of the Writer.
     * @param password the password of the Writer.
     * @return A ResponseEntity indicating whether authentication is successful or not and an HTTP status code.
     */
    @Operation(summary = "Login API for Writer.")
    @PostMapping("/writer/login")
    public ResponseEntity<String> writerLogin(String username, String password) {
        authService.writerLogin(username, password);
        String response = "Writer " + username + " successfully logged in.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * The current subject logs out and HTTP session gets invalidated.
     *
     * @param session the current HTTP session.
     * @return A ResponseEntity containing a String message and an HTTP status code
     */
    @Operation(summary = "Log out page.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        authService.logout();
        session.invalidate();
        String response = "Successfully logged out.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Below is the Registration service section.
     */

    /**
     * An endpoint for registering a User.
     *
     * @param user User to be registered.
     * @return A ResponseEntity containing registration information.
     */
    @Operation(summary = "Registration API for User.")
    @PostMapping("/user/register")
    @RequiresRoles(PredefinedRole.ADMIN_ROLE)
    public ResponseEntity<RegInfo> userRegister(@RequestBody User user) {
        RegInfo info = authService.registerUser(user);
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    /**
     * An endpoint for registering a Reader.
     *
     * @param reader Reader to be registered.
     * @return A ResponseEntity containing registration information.
     */
    @Operation(summary = "Registration API for Reader.")
    @PostMapping("/reader/register")
    public ResponseEntity<RegInfo> readerRegister(@RequestBody Reader reader) {
        RegInfo info = authService.registerReader(reader);
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    /**
     * An endpoint for registering a Writer.
     *
     * @param writer Writer to be registered.
     * @return A ResponseEntity containing registration information.
     */
    @Operation(summary = "Registration API for Writer.")
    @PostMapping("/writer/register")
    public ResponseEntity<RegInfo> writerRegister(@RequestBody Writer writer, @RequestParam int authorId) {
        RegInfo info = authService.registerWriter(writer, authorId);
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    /*
     * Other useful endpoints.
     */


    @Operation(summary = "Need to login page.")
    @GetMapping("/toLogin")
    @ResponseBody
    public String toLogin() {
        return "You are directed to the toLogin page.";
    }

    @Operation(summary = "No authorization page.")
    @GetMapping("/unauthorized")
    @ResponseBody
    public String noAuth() {
        return "Unauthorized to view.";
    }


}
