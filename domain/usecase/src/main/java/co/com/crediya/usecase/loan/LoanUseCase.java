package co.com.crediya.usecase.loan;

import co.com.crediya.model.loan.Loan;
import co.com.crediya.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanUseCase {
    private final LoanRepository loanRepository;

    public Mono<Loan> saveLoan(Loan loan){
        return loanRepository.saveLoan(loan);
    }

}
