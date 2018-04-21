package de.eimantas.edgeservice.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.dto.Expenses;

@RestController
public class ExpensesController {
	private final ExpensesClient expensesClient;

	public ExpensesController(ExpensesClient expensesClient) {
		this.expensesClient = expensesClient;
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/open-expenses")
	public Collection<Expenses> openExpenses() {
		return expensesClient.readExpenses().getContent().stream().filter(this::isOpen).collect(Collectors.toList());
	}

	private boolean isOpen(Expenses expenses) {
		return !expenses.getName().equals("Ferrari") && !expenses.getName().equals("Bugatti");
	}

	public Collection<Expenses> fallback() {
		return new ArrayList<>();
	}
}
