package co.com.crediya.api.exception;

import co.com.crediya.model.exception.CustomBaseException;
import co.com.crediya.model.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        if(ex instanceof BusinessException businessException){

            BussinessResponseException responseError = new BussinessResponseException(
                businessException.getSuccess(),
                businessException.getMessage(),
                businessException.getTimestamp(),
                businessException.getErrorCode()
            );

            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(businessException.getStatusCode()));
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(
                Mono.fromCallable(() -> objectMapper.writeValueAsBytes(responseError))
                    .map(bytes -> exchange.getResponse().bufferFactory().wrap(bytes))
            );
        }

        if(ex instanceof ConstraintViolationException constraintViolationException){
            CustomResponseException responseError = new CustomResponseException(
                    constraintViolationException.getSuccess(),
                    constraintViolationException.getMessage(),
                    constraintViolationException.getTimestamp(),
                    constraintViolationException.getErrorCode(),
                    constraintViolationException.getErrors()
            );

            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(constraintViolationException.getStatusCode()));
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(
                    Mono.fromCallable(() -> objectMapper.writeValueAsBytes(responseError))
                            .map(bytes -> exchange.getResponse().bufferFactory().wrap(bytes))
            );
        }

        // Fallback para errores inesperados
        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(500));
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(
                Mono.fromCallable(() -> objectMapper.writeValueAsBytes(new UnexpectedErrorException(ex)))
                        .map(bytes -> exchange.getResponse().bufferFactory().wrap(bytes))
        );


    }
}
