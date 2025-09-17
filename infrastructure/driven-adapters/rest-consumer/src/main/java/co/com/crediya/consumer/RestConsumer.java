package co.com.crediya.consumer;

import co.com.crediya.consumer.exception.ErrorResponse;
import co.com.crediya.model.clientrest.ClientRest;
import co.com.crediya.model.clientrest.gateways.ClientRepository;
import co.com.crediya.model.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.rmi.UnexpectedException;

@Service
@RequiredArgsConstructor
public class RestConsumer implements ClientRepository {
    private final WebClient client;

    @Override
    public Mono<ClientRest> existsByEmailAndDocument(String email, String document) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users")
                        .queryParam("email", email)
                        .queryParam("documentid", document)
                        .build())
                .retrieve()
                .onStatus(HttpStatus.UNAUTHORIZED::equals,
                        resp -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)))
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(ErrorResponse.class)
                                .flatMap(error -> Mono.error(new BusinessException(error.getErrorCode(), error.getMessage(), resp.statusCode().value()))))
                .onStatus(HttpStatusCode::is5xxServerError,
                        resp -> resp.bodyToMono(UnexpectedException.class)
                        .flatMap(error -> Mono.error(new UnexpectedException(error.getMessage()))))
                .bodyToMono(new ParameterizedTypeReference<ObjectResponse<ClientRest>>() {})
                .flatMap(response -> {
                    if (response.getData() != null) {
                        return Mono.just(response.getData());
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
