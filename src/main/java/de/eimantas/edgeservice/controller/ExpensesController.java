package de.eimantas.edgeservice.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.dto.Expense;

@RestController
public class ExpensesController {
	private final ExpensesClient expensesClient;

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	public ExpensesController(ExpensesClient expensesClient) {
		this.expensesClient = expensesClient;
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/open-expenses")
	@CrossOrigin(origins = "*")
	public Collection<Expense> openExpenses() {
		logger.info("edge expenses request");
		Resources<Expense> expenses = expensesClient.readExpenses();
		logger.info("expenses count: " + expenses.getContent().size());
		return expensesClient.readExpenses().getContent().stream().filter(this::isOpen).collect(Collectors.toList());
	}

	private boolean isOpen(Expense expenses) {
		logger.info("checking expense : " + expenses.toString());
		return !expenses.getName().equals("Ferrari") && !expenses.getName().equals("Bugatti");
	}

	public Collection<Expense> fallback() {
		List<Expense> list = new ArrayList<>();
		list.add(new Expense(0L, "NO DATA", "test", BigDecimal.ZERO));
		return list;
	}
}
