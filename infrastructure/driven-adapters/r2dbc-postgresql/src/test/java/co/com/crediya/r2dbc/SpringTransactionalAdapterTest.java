package co.com.crediya.r2dbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SpringTransactionalAdapterTest {

    @Mock
    private TransactionalOperator transactionalOperator;

    private SpringTransactionalAdapter springTransactionalAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        springTransactionalAdapter = new SpringTransactionalAdapter(transactionalOperator);
    }

    @Test
    @DisplayName("Should execute a transactional operation successfully")
    void testExecuteTransaction() {
        Mono<String> operation = Mono.just("Test Operation");
        when(transactionalOperator.transactional(any(Mono.class))).thenAnswer(i -> Mono.just("Test Operation"));

        StepVerifier.create(springTransactionalAdapter.executeOne(() -> operation))
                .expectNext("Test Operation")
                .verifyComplete();
    }

    @Test
    @DisplayName("Should propagate error if transactional operation fails")
    void testExecuteTransactionWithError() {
        Mono<String> operation = Mono.error(new RuntimeException("Test Error"));
        when(transactionalOperator.transactional(any(Mono.class))).thenAnswer(i -> Mono.error(new RuntimeException("Test Error")));

        StepVerifier.create(springTransactionalAdapter.executeOne(() -> operation))
                .expectErrorMessage("Test Error")
                .verify();
    }

    @Test
    @DisplayName("Should execute a many transactional operations successfully")
    void testExecuteManyTransaction() {
        Flux<String> operation = Flux.just("Test Operation");
        when(transactionalOperator.transactional(any(Flux.class))).thenAnswer(i -> Flux.just("Test Operation"));
        StepVerifier.create(springTransactionalAdapter.executeMany(() -> operation))
                .expectNext("Test Operation")
                .verifyComplete();
    }
}
