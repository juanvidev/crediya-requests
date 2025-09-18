package co.com.crediya.r2dbc;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.TransactionalOperatorGateway;
import co.com.crediya.r2dbc.entity.LoanApplicationEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class LoanApplicationReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanApplication,
        LoanApplicationEntity,
        Integer,
        LoanApplicationReactiveRepository
> implements LoanApplicationRepository {

    private final TransactionalOperatorGateway transactionalOperatorGateway;

    public LoanApplicationReactiveRepositoryAdapter(LoanApplicationReactiveRepository repository, ObjectMapper mapper, TransactionalOperatorGateway transactionalOperatorGateway) {
        super(repository, mapper, d -> mapper.map(d, LoanApplication.class));
        this.transactionalOperatorGateway = transactionalOperatorGateway;
    }

    @Override
    public Mono<LoanApplication> save(LoanApplication loanApplication) {
        return transactionalOperatorGateway.executeOne(() -> super.save(loanApplication));
    }

    @Override
    public Flux<LoanApplication> findAllApplicationsByStatus(int statusId, int page, int size) {
        System.out.println("Finding applications with statusId: " + statusId + ", page: " + page + ", size: " + size);
        return this.transactionalOperatorGateway.executeMany(() ->
                super.repository.findAllApplicationsByStatus(statusId, page, size)
        ).map(entity -> mapper.map(entity, LoanApplication.class));
    }

}
