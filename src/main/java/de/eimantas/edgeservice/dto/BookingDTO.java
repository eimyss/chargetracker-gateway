package de.eimantas.edgeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDTO {
  private int id;
  private String name;
  private LocalDateTime startdate;
  private LocalDateTime endDate;
  private int project_id;
}
