package de.eimantas.edgeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryAndCountOverview {

  private ExpenseCategory category;
  private long count;

}
