package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.Helper.RequestHelper;
import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.client.ExpensesRootlessClient;
import de.eimantas.edgeservice.controller.expcetions.BadRequestException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/expenses")
public class ExpensesController {

  private final ExpensesClient expensesClient;

  @Autowired
  private ExpensesRootlessClient rootlessClient;

  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


  public ExpensesController(ExpensesClient expensesClient) {
    this.expensesClient = expensesClient;
  }

  //@HystrixCommand(fallbackMethod = "fallback")
  @GetMapping("/all")
  @CrossOrigin(origins = "*")

  public ResponseEntity<List> openExpenses() {
    logger.info("edge all expenses request");
    ResponseEntity<List> expenses = expensesClient.getAllExpenses();
    logger.info("got response ");
    return expenses;
  }


  @GetMapping("/search")
  @CrossOrigin(origins = "*")
  public ResponseEntity searchExpense(@RequestParam("name") String name) {
    logger.info("edge search expenses requiest with string: " + name);
    ResponseEntity antwort = expensesClient.searchExpenses(name);
    logger.info("expenses is done");
    return antwort;
  }

  // @HystrixCommand(fallbackMethod = "fallback")
  @GetMapping("/overview/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity getExpensesOverview(@PathVariable long id) {
    logger.info("edge expenses request");
    ResponseEntity expenses = expensesClient.getExpensesOverview(id);
    logger.info("expense overview response: " + expenses.toString());
    return expenses;
  }

  //   @HystrixCommand(fallbackMethod = "fallback")
  @GetMapping("/global")
  @CrossOrigin(origins = "*")
  public ResponseEntity getGlobalOverview(Principal principal) {
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

    ResponseEntity expenses = expensesClient.getGlobalOverview();
    logger.info("global overview response: " + expenses.toString());
    return expenses;
  }


  @GetMapping(value = "/get/period", produces = MediaType.APPLICATION_JSON_VALUE)
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> getExpensesInPeriod(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws BadRequestException {

    if (fromDate == null || toDate == null) {
      throw new BadRequestException("From and To date cannot be null");
    }

    logger.info("getting expenses in period from " + fromDate.toString() + " to " + toDate.toString());
    SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd");
    ResponseEntity<List> expenses = expensesClient.getExpensesInPeriod(formatted.format(fromDate), formatted.format(toDate));
    logger.info("found some expenses");

    return expenses;


  }

  @PostMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity persistExpense(@RequestBody Object expense) {
    logger.info("saving expense : " + expense.toString());
    ResponseEntity response = expensesClient.postExpense(expense);
    logger.info("saved : " + expense.toString());
    return response;
  }

  @PutMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity updateExpense(@RequestBody Object expense) {
    logger.info("updating expense : " + expense.toString());
    ResponseEntity response = expensesClient.updateExpense(expense);
    logger.info("updated : " + expense.toString());
    return response;

  }


  @GetMapping("/account/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> getExpensesForAccount(@PathVariable long id) {
    logger.info("list expense request for account id " + id);
    ResponseEntity<List> expenses = expensesClient.getExpensesForAccount(id);
    logger.info("returning some expenses");
    return expenses;
  }


  @GetMapping("/get/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity getExpenseById(@PathVariable long id) {
    logger.info("one expense request");
    ResponseEntity response = expensesClient.getExpenseById(id);
    return response;
  }


  @GetMapping("/import/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity importExpensesForAccount(@PathVariable long id) {
    logger.info("import expense request");
    ResponseEntity response = expensesClient.importExpenses(id);
    return response;
  }


  @GetMapping("/user")
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> getUserExpenses() {
    logger.info("edge get user expenses");
    ResponseEntity<List> response = expensesClient.getUserExpenses();
    logger.info("found expenses count: ");
    return response;
  }


  @GetMapping("/populate")
  @CrossOrigin(origins = "*")
  public ResponseEntity populateExpenses() {
    logger.info("edge populate expenses");
    expensesClient.populateExpenses();
    return ResponseEntity.ok("done");
  }


  @GetMapping("/types")
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> getExpensesTypes() {
    logger.info("edge get expenses types");
    ResponseEntity<List> response = expensesClient.getExpenseTypes();
    return response;
  }

  @GetMapping("/backend/keys")
  @CrossOrigin(origins = "*")
  public Collection<String> getInfoKeys() {
    logger.info("edge get Information keys for backend");

    ResponseEntity<Object> response = rootlessClient.getServerInfo();
    logger.info("response : " + response.toString());
    logger.info("Links: " + ((LinkedHashMap) response.getBody()).get("_links"));
    LinkedHashMap links = (LinkedHashMap) ((LinkedHashMap) response.getBody()).get("_links");
    Set<String> keys = links.keySet();

    logger.info("got keys size: " + keys.size());

    return keys;
  }


  @GetMapping("/backend/keys/{key}")
  @CrossOrigin(origins = "*")
  public ResponseEntity<String> getInfoKeys(@PathVariable String key) throws IOException {
    logger.info("edge get Information for backend in key: " + key);

    ResponseEntity<Object> response = rootlessClient.getServerInfo();
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
