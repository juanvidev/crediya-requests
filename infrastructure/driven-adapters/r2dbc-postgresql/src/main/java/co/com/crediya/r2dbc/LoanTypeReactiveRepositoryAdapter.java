package co.com.crediya.r2dbc;

import co.com.crediya.model.TransactionalOperatorGateway;
import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.loantype.gateways.LoanTypeRepository;
import co.com.crediya.r2dbc.entity.LoanTypeEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class LoanTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType,
        LoanTypeEntity,
        Integer,
        LoanTypeReactiveRepository
        > implements LoanTypeRepository {

    private final TransactionalOperatorGateway transactionalOperatorGateway;

    public LoanTypeReactiveRepositoryAdapter(LoanTypeReactiveRepository repository, ObjectMapper mapper, TransactionalOperatorGateway transactionalOperatorGateway) {
        super(repository, mapper, d -> mapper.map(d, LoanType.class));
        this.transactionalOperatorGateway = transactionalOperatorGateway;
    }

    @Override
    public Mono<LoanType> findByName(String name) {
        return transactionalOperatorGateway.executeOne(() -> super.repository.findByName(name));
    }

    @Override
    public Flux<LoanType> findByAll() {
        return null;
    }
}
