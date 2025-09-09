package co.com.crediya.model.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CustomBaseException extends RuntimeException {
    private int statusCode;
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private List<String> errors;

    public CustomBaseException(String errorCode, String message,  int statusCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public CustomBaseException(String errorCode, String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }
}

