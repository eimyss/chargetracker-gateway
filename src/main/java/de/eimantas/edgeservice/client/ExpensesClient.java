package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.AccountOverView;
import de.eimantas.edgeservice.dto.ExpensesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

import de.eimantas.edgeservice.dto.Expense;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.ContentHandler;
import java.util.Collection;

@FeignClient(value = "expenses-catalog-servers", configuration = ExpensesClientConfig.class)
public interface ExpensesClient {

	@GetMapping("/raw-expenses")
	Collection<Expense> readExpenses();
}
