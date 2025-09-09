package co.com.crediya.api.docs;

import co.com.crediya.api.dto.LoanApplicationRequestDTO;
import co.com.crediya.api.exception.BussinessResponseException;
import co.com.crediya.api.exception.ConstraintViolationException;
import co.com.crediya.api.exception.UnexpectedErrorException;
import co.com.crediya.model.loanapplication.LoanApplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Schema(description = "Documentation for a loan application endpoint")
public class LoanApplicationDocs {

    @Operation(
        operationId = "createLoanApplication",
        summary = "Create a new loan application",
        description = "Creates a new application in the system",
        tags = {"LoanApplication"}
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Loan application created successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = BussinessResponseException.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UnexpectedErrorException.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflicts with inputs",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ConstraintViolationException.class))
        )
    })
    public Mono<LoanApplication> createLoanApplication(
        @RequestBody(description = "LoanApplication payload", required = true) LoanApplicationRequestDTO dto) {
        return Mono.empty();
    }
}
