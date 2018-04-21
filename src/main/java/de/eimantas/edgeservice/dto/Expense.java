package de.eimantas.edgeservice.dto;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

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
	@Id
	private Long id;
	private @NonNull String name;
	private String ort;
	private BigDecimal betrag;
}