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

    @NotNull(message = "Term in months is required")
    @Min(value = 6, message = "Term must be at least 6 months")
    @Max(value = 60, message = "Term must be at most 60 months")
    @Schema(description = "Term of the loan in months", example = "25")
    Integer term,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email of the applicant", example = "test@test.com")
    String email,

    @NotBlank(message = "Document ID is required")
    @Pattern(regexp = "\\d{10}", message = "Document ID must be exactly")
    @Schema(description = "Document ID of the applicant", example = "1234567890")
    String documentId,

    @NotBlank(message = "Type of loan name is required")
    @Schema(description = "Type of loan name", example = "Basic")
    String typeLoanName

){}
