package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.ExpenseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "expenses-catalog-servers-integration", configuration = ExpensesClientConfig.class)
public interface ExpensesClient {

	@GetMapping("/expense/get/{id}")
	ResponseEntity getExpenseById(@PathVariable long id);

    @PostMapping("/expense/add")
    ResponseEntity postExpense(@RequestBody ExpenseDTO expense);

	@GetMapping("/expenses/search")
	ResponseEntity searchExpenses(@RequestParam("name") String name);

	@GetMapping("/expense/populate")
	void populateExpenses();

	@GetMapping("/expense/get/all")
	ResponseEntity getAllExpenses();

	@GetMapping("/expense/user-expenses")
	ResponseEntity getUserExpenses();




}
