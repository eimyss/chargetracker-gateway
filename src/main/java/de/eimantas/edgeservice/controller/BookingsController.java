package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.client.BookingsClient;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/bookings")
public class BookingsController {

  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

  @Inject
  BookingsClient bookingsClient;

  @PostMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity persistAccount(@RequestBody List<Object> bookingDTO) {
    logger.info("saving booking : " + bookingDTO.toString());
    ResponseEntity responseEntity = bookingsClient.postBooking(bookingDTO.get(0));
    return responseEntity;
  }

  @GetMapping("/get/{id}")
  @CrossOrigin(origins = "*")
  public ResponseEntity getBookingById(@PathVariable long id) {
    logger.info("edge accoung get request for id: " + id);
    ResponseEntity booking = bookingsClient.getBookingById(id);
    logger.info("account id response: " + booking.toString());
    return booking;
  }

  @GetMapping("/all")
  @CrossOrigin(origins = "*")
  public ResponseEntity<List> allBookings() {
    logger.info("edge all expenses request");
    ResponseEntity<List> bookings = bookingsClient.getAllBookings();
    logger.info("got response with size " + bookings.getBody().size());
    return bookings;
  }


}
