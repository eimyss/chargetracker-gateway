package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "${feign.client.config.projects.service}", configuration = ClientConfig.class)
@RequestMapping(value = "/project")
public interface ProjectsClient {

  @GetMapping("/get/{id}")
  ResponseEntity<?> getProjectById(@PathVariable(name = "id") long id);

  @GetMapping("/get/all")
  ResponseEntity<List> getProjectList();

  @PostMapping("/save")
  ResponseEntity<?>  postProject(@RequestBody Object account);


  @GetMapping("/get/address/all")
  ResponseEntity<List> getAllAddresses();

  @GetMapping("/get/{id}/address")
  ResponseEntity<?> getProjectAddress(@PathVariable(name = "id") long id);

  @GetMapping( "/get/address/{id}")
  ResponseEntity<?> getAddressById(@PathVariable(name = "id") long id);


}
