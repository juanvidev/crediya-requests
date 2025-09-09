package co.com.crediya.model;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public interface TransactionalOperatorGateway {
    <T> Mono<T> executeOne(Supplier<Mono<T>> operation);
    <T> Flux<T> executeMany(Supplier<Flux<T>> operation);
}
