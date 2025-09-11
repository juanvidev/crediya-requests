package co.com.crediya.model.clientrest;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientRest {
    private Integer id;
    private String name;
    private String lastName;
    private String documentId;
    private String email;
    private BigDecimal baseSalary;
}
