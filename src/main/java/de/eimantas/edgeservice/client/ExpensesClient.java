package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

import de.eimantas.edgeservice.dto.Expense;

@FeignClient(value = "expenses-catalog-servers", configuration = ExpensesClientConfig.class)
public interface ExpensesClient {

	@GetMapping("/expenses")
	Resources<Expense> readExpenses();

}
