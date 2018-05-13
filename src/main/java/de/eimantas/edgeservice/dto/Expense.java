package de.eimantas.edgeservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private Long id;
    private @NonNull String name;
    private String ort;
    private  User user;
    private Instant createDate;
    private boolean expensed;
    private boolean periodic;
    private Account account;
    private BigDecimal betrag;

    private String category;
}