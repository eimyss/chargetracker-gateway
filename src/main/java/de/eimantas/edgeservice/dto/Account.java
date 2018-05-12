package de.eimantas.edgeservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {


	private Long id;
	private @NonNull String name;
	private  List<Expense> expenses;
	private User user;
}
