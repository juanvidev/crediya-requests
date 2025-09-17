package co.com.crediya.config;

import co.com.crediya.model.clientrest.gateways.ClientRepository;
import co.com.crediya.model.clientrest.gateways.TokenGateway;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.loantype.gateways.LoanTypeRepository;
import co.com.crediya.model.status.gateways.StatusRepository;
import co.com.crediya.usecase.loanapplication.LoanApplicationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.crediya.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {


    @Bean
    LoanApplicationUseCase loanApplicationUseCase (LoanApplicationRepository loanApplicationRepository, LoanTypeRepository loanTypeRepository, StatusRepository statusRepository, ClientRepository clientRepository, TokenGateway tokenGateway) {

        return new LoanApplicationUseCase(loanApplicationRepository, loanTypeRepository, statusRepository, clientRepository, tokenGateway);
    }
}
