package co.com.crediya.r2dbc;

import co.com.crediya.model.TransactionalOperatorGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class SpringTransactionalAdapter implements TransactionalOperatorGateway {

    private final TransactionalOperator transactionalOperator;

    @Override
    public <T> Mono<T> executeOne(Supplier<Mono<T>> operation) {
        return transactionalOperator.transactional(Mono.defer(operation));
    }

    @Override
    public <T> Flux<T> executeMany(Supplier<Flux<T>> operation) {
        return transactionalOperator.transactional(Flux.defer(operation));
    }
}
