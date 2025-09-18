package co.com.crediya.r2dbc;

import co.com.crediya.model.TransactionalOperatorGateway;
import co.com.crediya.model.status.Status;
import co.com.crediya.model.status.gateways.StatusRepository;
import co.com.crediya.r2dbc.entity.StatusEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class StatusReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Status,
        StatusEntity,
        Integer,
        StatusReactiveRepository
> implements StatusRepository {

    private final TransactionalOperatorGateway transactionalOperatorGateway;

    public StatusReactiveRepositoryAdapter(StatusReactiveRepository repository, ObjectMapper mapper, TransactionalOperatorGateway transactionalOperatorGateway) {
        super(repository, mapper, d -> mapper.map(d, Status.class));
        this.transactionalOperatorGateway = transactionalOperatorGateway;
    }

    @Override
    public Mono<Status> findByName(String name) {
        return transactionalOperatorGateway.executeOne(() -> super.repository.findByName(name));
    }

    @Override
    public Flux<Status> findAllById(List<Integer> ids) {
        return transactionalOperatorGateway.executeMany(() -> super.repository.findAllById(ids))
                .map(entity -> mapper.map(entity, Status.class));
    }
}
