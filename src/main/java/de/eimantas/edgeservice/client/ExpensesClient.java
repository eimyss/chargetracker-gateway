package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.ExpenseCategory;
import de.eimantas.edgeservice.dto.ExpenseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@FeignClient(value = "${feign.client.config.expenses.name}", configuration = ExpensesClientConfig.class)
public interface ExpensesClient {

	@GetMapping("/expense/get/{id}")
	ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable(name = "id") long id);

    @PostMapping("/expense/add")
	ExpenseDTO postExpense(@RequestBody ExpenseDTO expense);

	@PutMapping("/expense/add")
	ExpenseDTO updateExpense(@RequestBody ExpenseDTO expense);

	@GetMapping("/expenses/search")
	Collection<ExpenseDTO> searchExpenses(@RequestParam(name ="name") String name);

	@GetMapping("/expense/populate")
	ResponseEntity<String> populateExpenses();

	@GetMapping("/expense/account/{id}")
	ResponseEntity<List<ExpenseDTO>> getExpensesForAccount(@PathVariable(name = "id") long id);

	@GetMapping("/expense/csv/read/{id}")
	Collection<ExpenseDTO> importExpenses(@PathVariable(name = "id") long id);

	@GetMapping("/expense/get/all")
	Collection<ExpenseDTO> getAllExpenses();

	@GetMapping("/expense/user-expenses")
	Collection<ExpenseDTO> getUserExpenses();

	@GetMapping("/actuator")
	ResponseEntity<Object> getServerInfo();

	@GetMapping("/expense/types")
	Collection<ExpenseCategory> getExpenseTypes();




}
