package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "${feign.client.config.user.service}", configuration = ClientConfig.class)
@RequestMapping(value = "/user")
public interface UsersClient {

  @GetMapping("/get/{id}")
  ResponseEntity<?> getUserBykeykloackId(@PathVariable(name = "id") String id);

  @GetMapping("/me")
  ResponseEntity<?> getCurrentUser();

  @PostMapping("/save")
  ResponseEntity<?> saveUser(@RequestBody Object userdto);

}
