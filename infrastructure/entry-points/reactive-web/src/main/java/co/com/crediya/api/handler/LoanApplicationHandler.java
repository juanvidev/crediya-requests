package co.com.crediya.api.handler;

import co.com.crediya.api.dto.LoanApplicationRequestDTO;
import co.com.crediya.api.mapper.LoanApplicationMapper;
import co.com.crediya.api.util.ValidatorUtil;
import co.com.crediya.model.LoggerGateway;
import co.com.crediya.usecase.loanapplication.LoanApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoanApplicationHandler {

    private final LoanApplicationMapper loanApplicationMapper;
    private final ValidatorUtil validatorUtil;
    private final LoanApplicationUseCase loanApplicationUseCase;
    private static final String LOG_PREFIX = "[LoanApplicationHandler::createLoanApplication] ";
    private final LoggerGateway logger;

    public Mono<ServerResponse> saveApplication(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoanApplicationRequestDTO.class)
                .doOnNext(dto -> logger.info(LOG_PREFIX + " - Received payload"))
                .flatMap(validatorUtil::validate)
                .flatMap(dto -> loanApplicationUseCase.save(loanApplicationMapper.toDomain(dto), dto.typeLoanName()))
                .doOnNext(entity -> logger.info(LOG_PREFIX + " - LoanApplication created successfully: " + entity))
                .then(ServerResponse.status(HttpStatus.CREATED).build())
                .doOnSuccess(resp -> logger.info(LOG_PREFIX + " - Response sent with status 201"));

    }

}
