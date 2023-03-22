package book.demo.java.exception;

import org.springframework.http.HttpHeaders;

public class ExceptionUtil {

    public static HttpHeaders getHeaderForException(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Exception", message);
        return headers;
    }

}
