package co.com.crediya.consumer.exception;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ErrorResponse {
    private Boolean success;
    private String message;
    private LocalDateTime timestamp;
    private String code;

}