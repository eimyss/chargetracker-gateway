package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "expenses-catalog-servers-integration", configuration = ExpensesClientConfig.class)
public interface AccountsClient {

	@GetMapping("/account/overview/{id}")
	ResponseEntity readAccountOverview( @PathVariable long id);

    @GetMapping("/account/list")
    ResponseEntity getAccountList();

	@GetMapping("/account/global-overview")
	ResponseEntity getGlobalOverview();

	@GetMapping("/account/overview/expenses/{id}")
	ResponseEntity getExpensesOverview(@PathVariable long id);
}
