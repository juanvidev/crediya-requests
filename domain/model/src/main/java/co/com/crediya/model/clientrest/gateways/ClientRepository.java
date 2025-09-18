package co.com.crediya.model.clientrest.gateways;

import co.com.crediya.model.clientrest.ClientRest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientRepository {
    Mono<ClientRest> existsByEmailAndDocument(String email, String document);
    Flux<ClientRest> findAllByEmail(List<String> emails);
}
