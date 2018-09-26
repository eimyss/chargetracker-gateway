package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.AccountDTO;
import de.eimantas.edgeservice.dto.AccountOverViewDTO;
import de.eimantas.edgeservice.dto.AllAccountsOverViewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "${feign.client.config.expenses.name}", configuration = ExpensesClientConfig.class)
public interface AccountsClient {

  @GetMapping("/account/overview/{id}")
  ResponseEntity<AccountOverViewDTO> readAccountOverview(@PathVariable(name = "id") long id);

  @GetMapping("/account/get/{id}")
  AccountDTO getAccountById(@PathVariable(name = "id") long id);

  @GetMapping("/account/list")
  ResponseEntity<List<AccountDTO>> getAccountList();

  @GetMapping("/account/global-overview")
  ResponseEntity<AllAccountsOverViewDTO> getGlobalOverview();

  @GetMapping("/account/overview/expenses/{id}")
  ResponseEntity<AccountOverViewDTO> getExpensesOverview(@PathVariable(name = "id") long id);

  @PostMapping("/account/save")
  ResponseEntity<AccountDTO> postAccount(@RequestBody AccountDTO account);

  @PutMapping("/account/save")
  ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO account);
}
