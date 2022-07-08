package com.example.shiftservice.infrastructure.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShiftNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ShiftNotFoundException(long shiftId) {
        super("Shift with id " + shiftId + " not found");
    }
}
