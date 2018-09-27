package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "${feign.client.config.expense.service}", configuration = ExpensesClientConfig.class)
@RequestMapping(value = "/expense")
public interface ExpensesClient {

  @GetMapping("/get/{id}")
  ResponseEntity<?>  getExpenseById(@PathVariable(name = "id") long id);

  @PostMapping("/add")
  ResponseEntity<?> postExpense(@RequestBody Object expense);

  @PutMapping("/add")
  ResponseEntity<?>  updateExpense(@RequestBody Object expense);

  @GetMapping("/search")
  ResponseEntity<List> searchExpenses(@RequestParam(name = "name") String name);

  @GetMapping("/populate")
  ResponseEntity<List>   populateExpenses();

  @GetMapping("/account/{id}")
  ResponseEntity<List> getExpensesForAccount(@PathVariable(name = "id") long id);

  @GetMapping("/csv/read/{id}")
  ResponseEntity<List>  importExpenses(@PathVariable(name = "id") long id);

  @GetMapping("/get/period")
  ResponseEntity<List> getExpensesInPeriod(@RequestParam("from") String fromDate, @RequestParam("to") String toDate);

  @GetMapping("/get/all")
  ResponseEntity<List> getAllExpenses();

  @GetMapping("/user-expenses")
  ResponseEntity<List> getUserExpenses();


  @GetMapping("/types")
  ResponseEntity<List> getExpenseTypes();


  @GetMapping("/overview/{id}")
  ResponseEntity<?>  readAccountOverview(@PathVariable(name = "id") long id);

  @GetMapping("/global-overview")
  ResponseEntity<?>  getGlobalOverview();

  @GetMapping("/overview/expenses/{id}")
  ResponseEntity<?>  getExpensesOverview(@PathVariable(name = "id") long id);


}
