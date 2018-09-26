package de.eimantas.edgeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {


  private Long id;
  private LocalDate joindate;
  String name;
  private String username;
  private String email;
  int accountCount;

}

