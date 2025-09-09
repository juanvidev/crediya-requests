package co.com.crediya.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanApplicationResponseDTO (
        BigDecimal amount,
        LocalDate term,
        String email,
        String typeLoanName
) {}
