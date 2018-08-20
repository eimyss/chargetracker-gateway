package de.eimantas.edgeservice.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllAccountsOverViewDTO {

   private Map<Long, List<MonthAndAmountOverview>> overview;

   int monthBack;
   private LocalDateTime createDate;
   private long userId;


    List<ExpenseDTO> unexpenced;


    public void addNotExpenced(List<ExpenseDTO> expenseDTOS) {
        if (unexpenced == null)
            unexpenced = new ArrayList<>();
        unexpenced.addAll(expenseDTOS);
    }
}
