package co.com.crediya.model.applicationclient;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Pageable<T> {
    private Integer page;
    private Integer size;
    private Integer totalElements;
    private Integer totalPages;
    private List<T> content;
}
