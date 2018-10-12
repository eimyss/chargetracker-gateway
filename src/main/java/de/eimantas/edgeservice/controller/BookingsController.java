package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.dto.BookingDTO;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bookings")
public class BookingsController {

  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping("/save")
  @CrossOrigin(origins = "*")
  public ResponseEntity persistAccount(@RequestBody List<BookingDTO> bookingDTO) {
    logger.info("saving booking : " + bookingDTO.toString());
    BookingDTO booking = bookingDTO.get(0);
    booking.setProject_id(12);
    return ResponseEntity.ok(booking);
  }

}
