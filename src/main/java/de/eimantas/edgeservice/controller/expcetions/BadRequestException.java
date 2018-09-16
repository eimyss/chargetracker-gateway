package de.eimantas.edgeservice.controller.expcetions;

import de.eimantas.edgeservice.dto.ExpenseDTO;

import java.util.Collection;

public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }

}
