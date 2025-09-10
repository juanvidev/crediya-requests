package co.com.crediya.consumer;

import co.com.crediya.model.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements ClientRepository {
    private final WebClient client;

    @Override
    public Mono<Boolean> existsByEmailAndDocument(String email, String document) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users")
                        .queryParam("email", email)
                        .queryParam("documentid", document)
                        .build())
                .retrieve()
                .bodyToMono(ObjectResponse.class)
                .map(response -> Boolean.TRUE.equals(response.getData()));
    }
}
