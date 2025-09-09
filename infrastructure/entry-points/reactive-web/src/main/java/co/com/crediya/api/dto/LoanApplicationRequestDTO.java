package co.com.crediya.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(name = "LoanApplicationRequestDTO", description = "Data required for loan application")
public record LoanApplicationRequestDTO (

    @NotNull(message = "Amount is required")
    @Schema(description = "Amount of the loan", example = "15000.00")
    BigDecimal amount,

    @NotNull(message = "Term date is required")
    @Future(message = "Term date must be in the future")
    @Schema(description = "Term of the loan in days", example = "2025-09-21")
    LocalDate term,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email of the applicant", example = "test@test.com")
    String email,

    @NotBlank(message = "Type of loan name is required")
    @Schema(description = "Type of loan name", example = "Basic")
    String typeLoanName

){}
