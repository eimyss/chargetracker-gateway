package de.eimantas.edgeservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.dto.ExpenseDTO;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class ExpensesController {
	private final ExpensesClient expensesClient;

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


	public ExpensesController(ExpensesClient expensesClient) {
		this.expensesClient = expensesClient;
	}

	//@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/expenses/all")
	@CrossOrigin(origins = "*")

	public Collection<ExpenseDTO> openExpenses() {
		logger.info("edge all expenses request");
		Collection<ExpenseDTO> expenses = expensesClient.getAllExpenses();
		return expenses;
	}



    @GetMapping("/expenses/search")
    @CrossOrigin(origins = "*")
    public  Collection<ExpenseDTO> searchExpense(@RequestParam("name") String name) {
        logger.info("edge search expenses requiest with string: " + name);
        Collection<ExpenseDTO> antwort = expensesClient.searchExpenses(name);
        return antwort;
    }


	@PostMapping("/save")
	@CrossOrigin(origins = "*")
	public ResponseEntity persistExpense(@RequestBody ExpenseDTO expense) {
		logger.info("saving expense : " + expense.toString());
		expensesClient.postExpense(expense);
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}


	@GetMapping("/expense/account/{id}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<List<ExpenseDTO>> getExpensesForAccount(@PathVariable long id) {
		logger.info("list expense request for account id " + id);
		ResponseEntity<List<ExpenseDTO>> expenses = expensesClient.getExpensesForAccount(id);
		logger.info("returning " + expenses.toString() + " expenses");
		return expenses;
	}


	@GetMapping("/expense/get/{id}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable long id) {
		logger.info("one expense request");
		ResponseEntity<ExpenseDTO> response = expensesClient.getExpenseById(id);
		return response;
	}


	@GetMapping("/expenses/user")
	@CrossOrigin(origins = "*")
	public Collection<ExpenseDTO> getUserExpenses() {
		logger.info("edge get user expenses");
		Collection<ExpenseDTO> response = expensesClient.getUserExpenses();
		return response;
	}


	@GetMapping("/expenses/populate")
	@CrossOrigin(origins = "*")
	public ResponseEntity populateExpenses() {
		logger.info("edge populate expenses");
	  	expensesClient.populateExpenses();
		return ResponseEntity.ok("done");
	}



	public ResponseEntity fallback(Throwable e) {
		logger.warn("faLLING BACK on get expenses");
		e.printStackTrace();
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
