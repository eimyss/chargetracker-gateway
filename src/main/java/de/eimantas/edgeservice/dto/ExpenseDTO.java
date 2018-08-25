package de.eimantas.edgeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDTO {


        private Long id;
        private
        String name;
        private String ort;
        private String purpose;
        Long userId;
        private Instant createDate;
        private LocalDate bookingDate;
        private boolean expensed;
        private boolean expensable;
        private boolean valid;
        private boolean periodic;
        Long accountId;
        private BigDecimal betrag;
        private String currency;
        private String category;
    }

