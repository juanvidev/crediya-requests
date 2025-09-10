package co.com.crediya.model.loanapplication;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class LoanApplication {

    private Integer id;
    private BigDecimal amount;
    private LocalDate term;
    private String email;
    private String documentId;

    private Integer stateId;
    private Integer typeLoanId;

}

