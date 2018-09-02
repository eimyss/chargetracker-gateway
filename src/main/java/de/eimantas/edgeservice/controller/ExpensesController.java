package de.eimantas.edgeservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.eimantas.edgeservice.Helper.RequestHelper;
import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.dto.ExpenseDTO;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

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
		logger.info("got response: " + expenses.toString());
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


	@GetMapping("/expenses/backend/keys")
	@CrossOrigin(origins = "*")
	public Collection<String> getInfoKeys() {
		logger.info("edge get Information keys for backend");

		ResponseEntity<Object> response  =  expensesClient.getServerInfo();
		logger.info("response : " + response.toString());
		logger.info("Links: " + ((LinkedHashMap) response.getBody()).get("_links"));
		LinkedHashMap links = (LinkedHashMap) ((LinkedHashMap) response.getBody()).get("_links");
		Set<String> keys = links.keySet();

		logger.info("got keys size: " + keys.size());

		return keys;
	}


	@GetMapping("/expenses/backend/keys/{key}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<String> getInfoKeys(@PathVariable String key) throws IOException {
		logger.info("edge get Information for backend in key: " + key);

		ResponseEntity<Object> response  =  expensesClient.getServerInfo();
		logger.info("response : " + response.toString());
		logger.info("Links: " + ((LinkedHashMap) response.getBody()).get("_links"));
		LinkedHashMap links = (LinkedHashMap) ((LinkedHashMap) response.getBody()).get("_links");


		LinkedHashMap values = (LinkedHashMap) links.get(key);

		if (values == null) {
			logger.info("key: " + key + " is not found in actuator");
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		String url = (String) values.get("href");
		logger.info("value of href: " + url);

		String content = RequestHelper.getInfoFromUrl(url);
		logger.info("content of url: " + content);

		return new ResponseEntity<String>(content, HttpStatus.OK);
	}


	public ResponseEntity fallback(Throwable e) {
		logger.warn("faLLING BACK on get expenses");
		e.printStackTrace();
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
