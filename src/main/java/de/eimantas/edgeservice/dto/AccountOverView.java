package de.eimantas.edgeservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AccountOverView {


    private Long id;
    @JsonProperty("refAccount")
    private Account refAccount;
    private BigDecimal total;
    private boolean active;
    private int countExpenses;
    private Instant createDate;


}
