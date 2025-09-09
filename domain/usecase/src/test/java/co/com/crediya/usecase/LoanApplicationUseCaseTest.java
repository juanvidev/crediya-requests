package co.com.crediya.usecase;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.loantype.gateways.LoanTypeRepository;
import co.com.crediya.model.status.Status;
import co.com.crediya.model.status.gateways.StatusRepository;
import co.com.crediya.usecase.loanapplication.LoanApplicationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationUseCaseTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;
    @Mock
    private LoanTypeRepository loanTypeRepositoryMock;
    @Mock
    private StatusRepository statusRepositoryMock;
    @Mock
    private LoanApplicationUseCase loanApplicationUseCaseMock;
    private LoanApplication loanApplyToTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loanApplicationUseCaseMock = new LoanApplicationUseCase(loanApplicationRepository, loanTypeRepositoryMock, statusRepositoryMock);
        loanApplyToTest = LoanApplication.builder()
                .amount(new BigDecimal("2500.22"))
                .term(LocalDate.of(2025, 8, 25))
                .email("test@test.com")
                .typeLoanId(1)
                .build();
    }

//
//    @Test
//    @DisplayName("Should create user successfully when email does not exist")
//    void CreateLoanApplicationSuccesfully() {
//        when(loanTypeRepositoryMock.findByName("Basic")).thenReturn(Mono.just(new LoanType()));
//        when(statusRepositoryMock.findByName("PENDING")).thenReturn(Mono.just(new Status()));
//        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(loanApplyToTest));
//
//        StepVerifier.create(loanApplicationUseCaseMock.save(loanApplyToTest, anyString()))
//                .expectNext(loanApplyToTest)
//                .verifyComplete();
//
//    }
//
//    @Test
//    @DisplayName("Should fail to create user when already exists")
//    void FailCreateLoanApplicationWhenExists() {
//
//        when(loanApplicationRepository.save(loanApplyToTest))
//                .thenReturn(Mono.just(loanApplyToTest));
//
//        StepVerifier.create(loanApplicationUseCaseMock.save(loanApplyToTest, anyString()))
//                .expectErrorMessage("USER_ALREADY_EXISTS")
//                .verify();
//    }
//
//    @Test
//    @DisplayName("Should fail to create user when repository fails")
//    void FailCreateLoanApplicationWhenRepositoryFails() {
//
//        when(loanApplicationRepository.save(loanApplyToTest))
//                .thenReturn(Mono.just(loanApplyToTest));
//
//        StepVerifier.create(loanApplicationUseCaseMock.save(loanApplyToTest, anyString()))
//                .expectErrorMessage("Database connection error")
//                .verify();
//    }



}

