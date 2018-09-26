package de.eimantas.edgeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO {


  private Long id;
  private LocalDate createDate;
  private LocalDate expireDate;
  private LocalDate updateDate;
  private boolean active;
  private boolean businessAccount;
  private String bank;
  private String name;
  private int expensescount;
  private UserDTO user;

}

