package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.AccountOverViewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${feign.client.config.expenses.name}", configuration = OverviewClientConfig.class)
public interface OverviewClient {

  @GetMapping("/account/overview/{id}")
  AccountOverViewDTO readOverview(@PathVariable("id") long id);
}
