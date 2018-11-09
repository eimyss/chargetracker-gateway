package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "${feign.client.config.transaction.service}", configuration = ClientConfig.class)
@RequestMapping(value = "/transaction/error")
public interface TransactionsErrorClient {


  @GetMapping("/get/entity/{id}")
  ResponseEntity<?>  getTransactionErrorById(@PathVariable(name = "id") long id);

  @GetMapping("/get/all")
  ResponseEntity<List>  getAllTransactionError(@PathVariable(name = "id") long id);

}
