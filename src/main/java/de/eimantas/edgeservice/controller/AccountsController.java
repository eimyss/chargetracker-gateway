package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.client.AccountsClient;
import de.eimantas.edgeservice.dto.AccountDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountsController {


  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private AccountsClient accountsClient;

  @GetMapping("/list")
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> getAccountList() {
    logger.info("edge expenses request");
    ResponseEntity<List> expenses = accountsClient.getAccountList();
    logger.info("account list response: " + expenses.toString());
    return expenses;
  }


  @GetMapping("/get/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity getAccountById(@PathVariable long id) {
    logger.info("edge accoung get request for id: " + id);
    ResponseEntity account = accountsClient.getAccountById(id);
    logger.info("account id response: " + account.toString());
    return account;
  }


  @PostMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity persistAccount(@RequestBody Object account) {
    logger.info("saving account : " + account.toString());
    ResponseEntity response = accountsClient.postAccount(account);
    return response;

  }


  @PutMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity<AccountDTO> updateAccount(@RequestBody Object account) {
    logger.info("updating account : " + account.toString());
    ResponseEntity response = accountsClient.updateAccount(account);
    logger.info("updated : " + response.getBody().toString());
    return response;

  }


  public ResponseEntity fallback(Throwable e) {
    logger.warn("faLLING BACK on get expenses");
    e.printStackTrace();
    logger.warn("failed to fallback",e);
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
