//package book.demo.java.exception;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//@ControllerAdvice
//public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
//
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
//                ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(ReaderNotFoundException.class)
//    public final ResponseEntity<ErrorDetails> handleReaderNotFoundExceptions(Exception ex, WebRequest request) throws Exception {
//
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
//                ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
////        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
////        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
////                "Total Errors:" + ex.getErrorCount() + " First Error:" + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), request.getDescription(false));
//
//        List<FieldError> fieldErrorList = ex.getFieldErrors();
//        Map<String, Object> errMap = new HashMap<>();
//        for (FieldError err: fieldErrorList) {
//            errMap.put(err.getField(), err.getDefaultMessage());
//        }
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errMap.toString(), request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//
//    }
//}
