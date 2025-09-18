package co.com.crediya.model.applicationclient;


import co.com.crediya.model.clientrest.ClientRest;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationClient {
    private LoanApplicationCreator loanApplicationCreator;
    private ClientRest clientRest;

    private BigDecimal totalMonthlyPayment;
    private BigDecimal totalInterest;
}