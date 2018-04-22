package de.eimantas.edgeservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Expense {
	
	private @NonNull Long id;
	private @NonNull String name;
	private String ort;
	private BigDecimal betrag;
}