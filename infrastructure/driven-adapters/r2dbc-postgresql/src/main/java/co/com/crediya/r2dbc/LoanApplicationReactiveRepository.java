package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.entity.LoanApplicationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanApplicationReactiveRepository extends ReactiveCrudRepository<LoanApplicationEntity, Integer>, ReactiveQueryByExampleExecutor<LoanApplicationEntity> {

    Mono<Boolean> existsByTypeLoanId(Integer typeLoanId);

    @Query("""
        SELECT * FROM applications
        WHERE id_state = :statusId
        OFFSET :offset
        LIMIT :limit
    """)
    Flux<LoanApplicationEntity> findAllApplicationsByStatus(int statusId, int offset, int limit);
}
