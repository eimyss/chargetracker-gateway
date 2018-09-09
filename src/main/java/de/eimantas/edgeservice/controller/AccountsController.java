package de.eimantas.edgeservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.eimantas.edgeservice.client.AccountsClient;
import de.eimantas.edgeservice.dto.AccountDTO;
import de.eimantas.edgeservice.dto.AccountOverViewDTO;
import de.eimantas.edgeservice.dto.AllAccountsOverViewDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountsController {


	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountsClient accountsClient;

	@GetMapping("/account/list")
	@CrossOrigin(origins = "*")
	public ResponseEntity<List<AccountDTO>> getAccountList() {
		logger.info("edge expenses request");
        ResponseEntity<List<AccountDTO>> expenses = accountsClient.getAccountList();
		logger.info("account list response: " + expenses.toString());
		return expenses;
	}


    @GetMapping("/account/overview/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AccountOverViewDTO> readOverviewForAccount(@PathVariable long id) {
        logger.info("edge account overview request");
        ResponseEntity<AccountOverViewDTO> overview = accountsClient.readAccountOverview(id);
        logger.info("expenses count: " + overview.toString());
        return overview;
    }

 //   @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/account/global")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AllAccountsOverViewDTO> getGlobalOverview() {
        logger.info("edge expenses request");
        ResponseEntity<AllAccountsOverViewDTO> expenses = accountsClient.getGlobalOverview();
        logger.info("account list response: " + expenses.toString());
        return expenses;
    }

   // @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/account/expenses/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AccountOverViewDTO>  getExpensesOverview(@PathVariable long id) {
        logger.info("edge expenses request");
        ResponseEntity<AccountOverViewDTO>  expenses = accountsClient.getExpensesOverview(id);
        logger.info("account list response: " + expenses.toString());
        return expenses;
    }

    @PostMapping("/account/save")
    @CrossOrigin(origins = "*")
    public ResponseEntity persistExpense(@RequestBody AccountDTO account) {
        logger.info("saving account : " + account.toString());
        ResponseEntity<AccountDTO> response=  accountsClient.postAccount(account);
        return response;

    }


	public ResponseEntity fallback(Throwable e) {
		logger.warn("faLLING BACK on get expenses");
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
