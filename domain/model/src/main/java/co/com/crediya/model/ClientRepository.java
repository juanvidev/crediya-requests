package co.com.crediya.model;

import reactor.core.publisher.Mono;

public interface ClientRepository {
    Mono<Boolean> existsByEmailAndDocument(String email, String document);
}
