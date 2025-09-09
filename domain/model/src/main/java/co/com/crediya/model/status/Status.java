package co.com.crediya.model.status;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Status {

    private Integer id;
    private String name;
    private String description;

}
