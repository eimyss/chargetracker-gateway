package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "${feign.client.config.account.service}", configuration = AccountsClientConfig.class)
@RequestMapping(value = "/account")
public interface AccountsClient {


  @GetMapping("/get/{id}")
  ResponseEntity getAccountById(@PathVariable(name = "id") long id);

  @GetMapping("/list")
  ResponseEntity<List> getAccountList();

  @GetMapping("/list/id")
  ResponseEntity<List> getAccountListIds();

  @PostMapping("/save")
  ResponseEntity postAccount(@RequestBody Object account);

  @PutMapping("/save")
  ResponseEntity updateAccount(@RequestBody Object account);
}
