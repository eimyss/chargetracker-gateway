package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "expenses-catalog-servers", configuration = ExpensesClientConfig.class)
public interface AccountsClient {

	@GetMapping("/account/overview/{id}")
	ResponseEntity readAccountOverview( @PathVariable(name = "id") long id);

    @GetMapping("/account/list")
    ResponseEntity<List<AccountDTO>> getAccountList();

	@GetMapping("/account/global-overview")
	ResponseEntity getGlobalOverview();

	@GetMapping("/account/overview/expenses/{id}")
	ResponseEntity getExpensesOverview(@PathVariable(name = "id") long id);

	@PostMapping("/account/save")
	ResponseEntity postExpense(@RequestBody AccountDTO account);
}
