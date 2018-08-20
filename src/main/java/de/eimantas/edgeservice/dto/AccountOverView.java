package de.eimantas.edgeservice.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AccountOverView {



    private Long id;
    private Long refAccountId;
    private BigDecimal total;
    private boolean active;
    private int countExpenses;
    private Instant createDate;


}
