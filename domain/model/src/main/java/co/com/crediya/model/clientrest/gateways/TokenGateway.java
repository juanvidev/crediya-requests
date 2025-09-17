package co.com.crediya.model.clientrest.gateways;

import reactor.core.publisher.Mono;

public interface TokenGateway {
    Mono<String> getToken();
    Mono<String> getClaim(String claimName);
}
