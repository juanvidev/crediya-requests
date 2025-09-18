package co.com.crediya.model.applicationclient;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class LoanApplicationCreator {
    private Integer id;
    private BigDecimal amount;
    private Integer term;
    private String email;
    private String documentId;

    private String state;
    private String typeLoan;

}

