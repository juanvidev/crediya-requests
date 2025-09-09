package co.com.crediya.r2dbc.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("types_loan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoanTypeEntity {

    @Id
    @Column("id")
    private Integer id;
    private String name;
    @Column("max_amount")
    private BigDecimal maxAmount;
    @Column("min_amount")
    private BigDecimal minAmount;
    @Column("tax_rate")
    private BigDecimal taxRate;
    private Boolean validation;
}