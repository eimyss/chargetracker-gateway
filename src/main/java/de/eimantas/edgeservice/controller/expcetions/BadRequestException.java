package de.eimantas.edgeservice.controller.expcetions;

public class BadRequestException extends Exception {

  public BadRequestException(String message) {
    super(message);
  }

}
