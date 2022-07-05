package com.example.shiftservice.infrastructure.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.time.LocalDate;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShiftNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ShiftNotFoundException(String email, LocalDate date) {
        super("Shift for employee with email " + email + " and date " + date + " not found");
    }
}
