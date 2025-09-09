package co.com.crediya.r2dbc.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("applications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoanApplicationEntity {

    @Id
    @Column("id")
    private Integer id;
    private BigDecimal amount;
    private LocalDate term;
    private String email;
    @Column("id_state")
    private Integer stateId;
    @Column("id_type_loan")
    private Integer typeLoanId;
}