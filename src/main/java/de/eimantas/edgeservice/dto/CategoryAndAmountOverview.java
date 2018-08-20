package de.eimantas.edgeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryAndAmountOverview {

    private ExpenseCategory name;
    private BigDecimal amount;
}
