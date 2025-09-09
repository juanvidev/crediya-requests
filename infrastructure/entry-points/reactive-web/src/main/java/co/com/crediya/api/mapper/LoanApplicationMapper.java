package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.LoanApplicationRequestDTO;
import co.com.crediya.api.dto.LoanApplicationResponseDTO;
import co.com.crediya.model.loanapplication.LoanApplication;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {

    LoanApplicationResponseDTO toResponse(LoanApplication loanApplication);

    LoanApplication toDomain(LoanApplicationRequestDTO loanApplicationRequestDTO);
}
