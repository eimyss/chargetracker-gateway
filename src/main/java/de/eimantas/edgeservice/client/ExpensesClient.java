package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.AccountOverView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

import de.eimantas.edgeservice.dto.Expense;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.ContentHandler;

@FeignClient(value = "expenses-catalog-servers", configuration = ExpensesClientConfig.class)
public interface ExpensesClient {

	@GetMapping("/expenses")
	Resources<Expense> readExpenses();

	@GetMapping("/account/overview/{id}")
	Resources<AccountOverView> readOverview( @PathVariable("id") long id);
}
