package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.AccountOverView;
import de.eimantas.edgeservice.dto.Expense;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "expenses-catalog-servers", configuration = OverviewClientConfig.class)
public interface OverviewClient {

	@GetMapping("/account/overview/{id}")
	AccountOverView readOverview(@PathVariable("id") long id);
}
