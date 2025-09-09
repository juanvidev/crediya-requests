package co.com.crediya.api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BussinessResponseException {
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
}
