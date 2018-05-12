package de.eimantas.edgeservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountOverView {


    private Long id;
    private Account refAccount;
    private BigDecimal total;
    private boolean active;
    private int countExpenses;
    private Instant createDate;


}
