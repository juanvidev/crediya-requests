package co.com.crediya.api.config;

import co.com.crediya.api.dto.LoanApplicationRequestDTO;
import co.com.crediya.api.dto.LoanApplicationResponseDTO;
import co.com.crediya.api.mapper.LoanApplicationMapper;
import co.com.crediya.model.loanapplication.LoanApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationMapperTest {

    private final LoanApplicationMapper mapper = Mappers.getMapper(LoanApplicationMapper.class);

    @Test
    @DisplayName("Should map LoanApplicationRequestDTO to LoanApplication entity correctly")
    void testToDomain() {
        LoanApplicationRequestDTO dto = new LoanApplicationRequestDTO(
                new BigDecimal("2500.22"),
                LocalDate.of(1995, 8, 25),
                "test@test.com",
                "1006352211",
                "Basic"

        );

        LoanApplication loanApply = mapper.toDomain(dto);

        assertNotNull(loanApply);
        assertEquals(dto.email(), loanApply.getEmail());
        assertEquals(dto.amount(), loanApply.getAmount());
        assertEquals(dto.term(), loanApply.getTerm());
    }

    @Test
    @DisplayName("Should map LoanApplication entity to LoanApplicationResponseDTO correctly")
    void testToResponse() {
        LoanApplication loanApply = LoanApplication.builder()
                .amount(new BigDecimal("2500.22"))
                .term(LocalDate.of(2025, 8, 25))
                .email("test@test.com")
                .typeLoanId(1)
                .build();

        LoanApplicationResponseDTO response = mapper.toResponse(loanApply);

        assertNotNull(response);
        assertEquals(loanApply.getEmail(), response.email());
        assertEquals(loanApply.getAmount(), response.amount());
        assertEquals(loanApply.getTerm(), response.term());
    }


    @Test
    @DisplayName("Should handle null values in mapping methods")
    void testNullMapping() {
        assertNull(mapper.toDomain(null));
        assertNull(mapper.toResponse(null));
    }

}