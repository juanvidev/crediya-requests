package co.com.crediya.r2dbc;

import co.com.crediya.model.status.Status;
import co.com.crediya.r2dbc.entity.StatusEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface StatusReactiveRepository extends ReactiveCrudRepository<StatusEntity, Integer>, ReactiveQueryByExampleExecutor<StatusEntity> {

    Mono<Status> findByName(String name);

}
