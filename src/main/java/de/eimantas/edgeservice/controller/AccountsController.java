package de.eimantas.edgeservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.eimantas.edgeservice.client.AccountsClient;
import de.eimantas.edgeservice.dto.AccountOverView;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {


	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountsClient accountsClient;

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/account/list")
	@CrossOrigin(origins = "*")
	public ResponseEntity getAccountList() {
		logger.info("edge expenses request");
        ResponseEntity expenses = accountsClient.getAccountList();
		logger.info("account list response: " + expenses.toString());
		return expenses;
	}


    @GetMapping("/account/overview/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AccountOverView> readOverviewForAccount(@PathVariable long id) {
        logger.info("edge account overview request");
        ResponseEntity overview = accountsClient.readAccountOverview(id);
        logger.info("expenses count: " + overview.toString());
        return new ResponseEntity(overview, HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/account/global")
    @CrossOrigin(origins = "*")
    public ResponseEntity getGlobalOverview() {
        logger.info("edge expenses request");
        ResponseEntity expenses = accountsClient.getGlobalOverview();
        logger.info("account list response: " + expenses.toString());
        return expenses;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/account/expenses/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity getExpensesOverview(@PathVariable long id) {
        logger.info("edge expenses request");
        ResponseEntity expenses = accountsClient.getExpensesOverview(id);
        logger.info("account list response: " + expenses.toString());
        return expenses;
    }


	public ResponseEntity fallback(Throwable e) {
		logger.warn("faLLING BACK on get expenses");
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
