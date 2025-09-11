package co.com.crediya.model.clientrest.gateways;

import co.com.crediya.model.clientrest.ClientRest;
import reactor.core.publisher.Mono;

public interface ClientRepository {
    Mono<ClientRest> existsByEmailAndDocument(String email, String document);
}
