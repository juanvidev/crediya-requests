package co.com.crediya.usecase.loanapplication;

import co.com.crediya.model.clientrest.gateways.ClientRepository;
import co.com.crediya.model.clientrest.gateways.TokenGateway;
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
    private final ClientRepository clientRepository;
    private final TokenGateway tokenGateway;

    public Mono<LoanApplication> save(LoanApplication loanApplication, String loanTypeName) {

        return tokenGateway.getClaim("documentId")
                .flatMap(documentIdToken -> documentIdToken.equals(loanApplication.getDocumentId())
                        ? Mono.just(documentIdToken)
                        : Mono.error(new BusinessException("BSS_04", "You do not have permission to create a loan application for this document ID")))
                .flatMap(documentId ->
                    clientRepository.existsByEmailAndDocument(
                        loanApplication.getEmail(),
                        documentId)
                )
                .switchIfEmpty(Mono.error(new BusinessException("BSS_03", "Client does not exist")))
                .then(loanTypeRepository.findByName(loanTypeName)
                        .switchIfEmpty(Mono.error(new BusinessException("BSS_01", "Loan type not found"))))
                .zipWith(statusRepository.findByName("PENDING")
                        .switchIfEmpty(Mono.error(new BusinessException("BSS_02", "Status not found"))))
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
