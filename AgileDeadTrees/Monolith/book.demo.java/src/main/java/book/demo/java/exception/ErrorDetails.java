package book.demo.java.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String exception;

    private final String response;

    private final String message;

    private final String path;

    private final String stacktrace;

    public ErrorDetails(String exception, String message, String path, String stacktrace) {
        this.exception = exception;
        this.message = message;
        this.path = path;
        this.stacktrace = stacktrace;
        this.response = "";
    }

    public ErrorDetails(String exception, String response, String message, String path, String stacktrace) {
        this.exception = exception;
        this.response = response;
        this.message = message;
        this.path = path;
        this.stacktrace = stacktrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getException() {
        return exception;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getResponse() {
        return response;
    }
}