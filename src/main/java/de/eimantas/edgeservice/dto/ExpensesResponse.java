package de.eimantas.edgeservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpensesResponse extends ResourceSupport {

    @JsonProperty("_embedded.expenses")
    private List<Expense> expenses;

}
