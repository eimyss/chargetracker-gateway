package de.eimantas.edgeservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private Long id;
    private @NonNull String name;
    private String ort;
    private  UserDTO user;
    private Instant createDate;
    private boolean expensed;
    private boolean periodic;
    private AccountDTO account;
    private BigDecimal betrag;

    private String category;
}