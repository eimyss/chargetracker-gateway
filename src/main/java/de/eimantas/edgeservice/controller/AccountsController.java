package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.client.AccountsClient;
import de.eimantas.edgeservice.dto.AccountDTO;
import de.eimantas.edgeservice.dto.AccountOverViewDTO;
import de.eimantas.edgeservice.dto.AllAccountsOverViewDTO;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
  public ResponseEntity<AllAccountsOverViewDTO> getGlobalOverview(Principal principal) {
    logger.info("edge expenses request");
    KeycloakAuthenticationToken userAuth = (KeycloakAuthenticationToken) principal;
    logger.info("-----------------");
    logger.info(userAuth.getDetails().toString());
    KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) userAuth.getPrincipal();
    RefreshableKeycloakSecurityContext ctx = (RefreshableKeycloakSecurityContext) keycloakPrincipal.getKeycloakSecurityContext();
    logger.info("ID: " + ctx.getToken().getSubject());
    logger.info(userAuth.getAccount().toString());
    logger.info(userAuth.getCredentials().toString());
    logger.info(userAuth.getPrincipal().toString());
    logger.info("-----------------");

    ResponseEntity<AllAccountsOverViewDTO> expenses = accountsClient.getGlobalOverview();
    logger.info("account list response: " + expenses.toString());
    return expenses;
  }

  // @HystrixCommand(fallbackMethod = "fallback")
  @GetMapping("/account/expenses/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity<AccountOverViewDTO> getExpensesOverview(@PathVariable long id) {
    logger.info("edge expenses request");
    ResponseEntity<AccountOverViewDTO> expenses = accountsClient.getExpensesOverview(id);
    logger.info("account list response: " + expenses.toString());
    return expenses;
  }

  @GetMapping("/account/get/{id}")
  @CrossOrigin(origins = "*")
  public AccountDTO getAccountById(@PathVariable long id) {
    logger.info("edge accoung get request for id: " + id);
    AccountDTO account = accountsClient.getAccountById(id);
    logger.info("account id response: " + account.toString());
    return account;
  }


  @PostMapping("/account/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity persistAccount(@RequestBody AccountDTO account) {
    logger.info("saving account : " + account.toString());
    ResponseEntity<AccountDTO> response = accountsClient.postAccount(account);
    return response;

  }


  @PutMapping("/account/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO account) {
    logger.info("updating account : " + account.toString());
    ResponseEntity<AccountDTO> response = accountsClient.updateAccount(account);
    logger.info("updated : " + response.getBody().toString());
    return response;

  }


  public ResponseEntity fallback(Throwable e) {
    logger.warn("faLLING BACK on get expenses");
    e.printStackTrace();
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
