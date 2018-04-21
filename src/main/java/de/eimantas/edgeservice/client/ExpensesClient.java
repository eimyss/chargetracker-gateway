package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import de.eimantas.edgeservice.dto.Expenses;

@FeignClient("expenses-catalog-servers")
public interface ExpensesClient {

    @GetMapping("/expenses")
    Resources<Expenses> readExpenses();
	
}
