package de.eimantas.edgeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDTO {


        private Long id;
        private
        String name;
        private String ort;
        Long userId;
        private Instant createDate;
        private boolean expensed;
        private boolean valid;
        private boolean expensable;
        private boolean periodic;
        Long accountId;
        private BigDecimal betrag;
        private String category;
    }

