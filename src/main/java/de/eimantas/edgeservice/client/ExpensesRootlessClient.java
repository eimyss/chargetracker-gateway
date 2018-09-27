package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "${feign.client.config.expense.service}", configuration = ExpensesClientConfig.class)
public interface ExpensesRootlessClient {

  @GetMapping("/actuator")
  ResponseEntity<Object> getServerInfo();

}
