package co.com.crediya.consumer;

import co.com.crediya.consumer.exception.ErrorResponse;
import co.com.crediya.model.clientrest.ClientRest;
import co.com.crediya.model.clientrest.gateways.ClientRepository;
import co.com.crediya.model.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(ErrorResponse.class)
                                .defaultIfEmpty(new ErrorResponse())
                                .flatMap(error -> Mono.error(
                                        new BusinessException(
                                                error.getCode() != null ? error.getCode() : "BSS_03",
                                                error.getMessage() != null ? error.getMessage() : "Client service error"
                                        )
                                ))                )
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
