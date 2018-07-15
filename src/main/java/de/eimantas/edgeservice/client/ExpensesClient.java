package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.Expense;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(value = "expenses-catalog-servers", configuration = ExpensesClientConfig.class)
public interface ExpensesClient {

	@GetMapping("/raw-expenses")
	Collection<Expense> readExpenses();

    @PostMapping("/raw-expenses")
    ResponseEntity<String> postExpense(@RequestBody Expense expense);

	@GetMapping("/expenses/search")
	Collection<Expense> searchExpenses(@RequestParam("name") String name);
}
