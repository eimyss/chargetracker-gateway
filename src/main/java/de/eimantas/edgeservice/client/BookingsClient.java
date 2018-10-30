package de.eimantas.edgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "${feign.client.config.expense.service}", configuration = ClientConfig.class)
@RequestMapping(value = "/booking")
public interface BookingsClient {

  @GetMapping("/get/{id}")
  ResponseEntity<?> getBookingById(@PathVariable(name = "id") long id);

  @PostMapping("/save")
  ResponseEntity<?> postBooking(@RequestBody Object booking);

  @GetMapping("/get/all")
  ResponseEntity<List> getAllBookings();


}
