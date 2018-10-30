package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.dto.AccountOverViewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${feign.client.config.expenses.name}", configuration = ClientConfig.class)
public interface OverviewClient {

  @GetMapping("/account/overview/{id}")
  ResponseEntity<?>  readOverview(@PathVariable("id") long id);
}
