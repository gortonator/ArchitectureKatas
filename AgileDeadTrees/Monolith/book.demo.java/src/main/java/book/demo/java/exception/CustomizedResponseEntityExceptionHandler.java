package book.demo.java.exception;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {

        return new ResponseEntity<>(
                new ErrorDetails(
                        ex.getClass().getName(),
                        ex.getMessage(),
                        request.getDescription(false),
                        Arrays.toString(ex.getStackTrace())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<ErrorDetails> handleNoSuchElementExceptions(NoSuchElementException ex,
                                                                            WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(
                        ex.getClass().getName(),
                        ex.getMessage(),
                        request.getDescription(false),
                        Arrays.toString(ex.getStackTrace())),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public final ResponseEntity<ErrorDetails> handleUnauthenticatedExceptions(UnauthenticatedException ex,
                                                                              WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(
                        ex.getClass().getName(),
                        ex.getMessage(),
                        request.getDescription(false),
                        Arrays.toString(ex.getStackTrace())),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorDetails> handleUnauthorizedExceptions(UnauthorizedException ex,
                                                                           WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(
                        ex.getClass().getName(),
                        ex.getMessage(),
                        request.getDescription(false),
                        Arrays.toString(ex.getStackTrace())),
                HttpStatus.FORBIDDEN);
    }

    /*
        Shiro Credential Exceptions
     */
    @ExceptionHandler(UnknownAccountException.class)
    public final ResponseEntity<String> handleUnknownAccountExceptions(UnknownAccountException ex,
                                                                       WebRequest request) {
        String response = "Username not found.";
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public final ResponseEntity<String> handleIncorrectCredentialsExceptions(IncorrectCredentialsException ex,
                                                                             WebRequest request) {
        String response = "Incorrect password.";
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountException.class)
    public final ResponseEntity<ErrorDetails> handleAccountExceptionExceptions(AccountException ex,
                                                                               WebRequest request) {
        String response = "Other Account Exception.";
        return new ResponseEntity<>(
                new ErrorDetails(
                        ex.getClass().getName(),
                        response,
                        ex.getMessage(),
                        request.getDescription(false),
                        Arrays.toString(ex.getStackTrace())),
                HttpStatus.UNAUTHORIZED);
    }

}

