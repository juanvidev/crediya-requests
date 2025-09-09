package co.com.crediya.usecase.loanapplication;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.loantype.gateways.LoanTypeRepository;
import co.com.crediya.model.status.gateways.StatusRepository;
import co.com.crediya.model.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanApplicationUseCase {
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StatusRepository statusRepository;

    public Mono<LoanApplication> save(LoanApplication loanApplication, String loanTypeName) {
        return loanTypeRepository.findByName(loanTypeName)
                .switchIfEmpty(Mono.error(new BusinessException("BSS_01", "Loan type not found")))
                .zipWith(statusRepository.findByName("PENDING"))
                .switchIfEmpty(Mono.error(new BusinessException("BSS_02", "Status not found")))
                .flatMap(tuple -> {
                    var loanType = tuple.getT1();
                    var status = tuple.getT2();

                    LoanApplication newLoanApplication = loanApplication.toBuilder()
                            .typeLoanId(loanType.getId())
                            .stateId(status.getId())
                            .build();
                    return loanApplicationRepository.save(newLoanApplication);
                });

    }
}
