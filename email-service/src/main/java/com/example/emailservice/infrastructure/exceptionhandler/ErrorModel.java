package com.example.emailservice.infrastructure.exceptionhandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorModel {

    private final HttpStatus status;

    private final LocalDateTime timestamp;

    private final String message;

    private final String details;

    public ErrorModel(HttpStatus status, String message, String details) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }
}
