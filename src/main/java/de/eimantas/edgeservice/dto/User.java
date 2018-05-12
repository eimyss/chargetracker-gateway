package de.eimantas.edgeservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {


	private Long id;
	private @NonNull String name;
	private @NonNull String username;
	private String email;
	private List<Account> accounts;


}
