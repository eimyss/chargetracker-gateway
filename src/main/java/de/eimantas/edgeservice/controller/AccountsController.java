package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.client.AccountsClient;
import de.eimantas.edgeservice.controller.expcetions.BadRequestException;
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
    ResponseEntity<List> accounts = accountsClient.getAccountList();
    logger.info("account list response: " + accounts.getBody().size());
    return accounts;
  }


  @GetMapping("/get/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity getAccountById(@PathVariable long id) {
    logger.info("edge accoung get request for id: " + id);
    ResponseEntity account = accountsClient.getAccountById(id);
    logger.info("account id response: " + account.toString());
    return account;
  }


  @GetMapping("/history/list")
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> getAllAccountsHistory() {
    logger.info("edge expenses request");
    ResponseEntity<List> histories = accountsClient.getAllAccountHistories();
    logger.info("account list response: " + histories.getBody().size());
    return histories;
  }


  @GetMapping("/history/get/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> getAccountHistoryById(@PathVariable long id) {
    logger.info("edge accoung get request for id: " + id);
    ResponseEntity<List> history = accountsClient.getAccountHistory(id);
    logger.info("account id response: " + history.getBody().size());
    return history;
  }


  @PostMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity persistAccount(@RequestBody Object account) throws BadRequestException {
    if (account != null) {
      logger.info("saving account : " + account.toString());
      ResponseEntity response = accountsClient.postAccount(account);
      return response;
    }

    logger.warn("passed object to save account is null");
    throw new BadRequestException("account is null");
  }


  @PutMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity<AccountDTO> updateAccount(@RequestBody Object account) throws BadRequestException {

    if (account != null) {
      logger.info("updating account : " + account.toString());
      ResponseEntity response = accountsClient.updateAccount(account);
      logger.info("updated : " + response.getBody().toString());
      return response;

    }
    logger.warn("passed object to update account is null");
    throw new BadRequestException("account is null");
  }


  public ResponseEntity fallback(Throwable e) {
    logger.warn("faLLING BACK on get expenses");
    e.printStackTrace();
    logger.warn("failed to fallback", e);
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
