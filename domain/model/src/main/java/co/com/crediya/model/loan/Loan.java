package co.com.crediya.model.loan;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan {

    private String id;
    private String amount;
    private String term;
    private String email;
    private String id_state;
    private String id_type_loan;

}

