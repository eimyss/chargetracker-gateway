package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "expenses-catalog-servers", configuration = ExpensesClientConfig.class)
public interface AccountsClient {

	@GetMapping("/account/overview/{id}")
	ResponseEntity readAccountOverview( @PathVariable long id);

    @GetMapping("/account/list")
    ResponseEntity getAccountList();

	@PostMapping("/account/save")
	ResponseEntity postExpense(@RequestBody AccountDTO account);

	@GetMapping("/account/global-overview")
	ResponseEntity getGlobalOverview();

	@GetMapping("/account/overview/expenses/{id}")
	ResponseEntity getExpensesOverview(@PathVariable long id);
}
