package com.example.emailservice.infrastructure.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Arrays;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidRequestException.class, IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidRequest(InvalidRequestException ex) {
        ErrorModel model = new ErrorModel(HttpStatus.BAD_REQUEST, "Invalid request", ex.getMessage());

        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Exception ex) {
        ErrorModel model = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
                Arrays.toString(ex.getStackTrace()));

        return new ResponseEntity<>(model, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


