package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "${feign.client.config.transaction.service}", configuration = ClientConfig.class)
@RequestMapping(value = "/transaction")
public interface TransactionsClient {

  @GetMapping("/get/{id}")
  ResponseEntity<?> getTransaction(@PathVariable(name = "id") long id);

  @GetMapping("/all")
  ResponseEntity<List> getAllTransactions();

  @GetMapping("/get/{type}/{id}")
  ResponseEntity<?> getTransactionByEntityAndType(@PathVariable(name = "type") String type, @PathVariable (name = "id") long id);

}
