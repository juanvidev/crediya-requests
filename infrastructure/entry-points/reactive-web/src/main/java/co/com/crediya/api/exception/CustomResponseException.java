package co.com.crediya.api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CustomResponseException {
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
    private List<String> errors;

}
