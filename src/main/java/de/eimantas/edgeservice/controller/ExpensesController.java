package de.eimantas.edgeservice.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.dto.Expenses;

@RestController
public class ExpensesController {
	private final ExpensesClient expensesClient;

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	public ExpensesController(ExpensesClient expensesClient) {
		this.expensesClient = expensesClient;
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/open-expenses")
	public Collection<Expenses> openExpenses() {
		logger.info("edge expenses request");
		return expensesClient.readExpenses().getContent().stream().filter(this::isOpen).collect(Collectors.toList());
	}

	private boolean isOpen(Expenses expenses) {
		return !expenses.getName().equals("Ferrari") && !expenses.getName().equals("Bugatti");
	}

	public Collection<Expenses> fallback() {
		return new ArrayList<>();
	}
}
