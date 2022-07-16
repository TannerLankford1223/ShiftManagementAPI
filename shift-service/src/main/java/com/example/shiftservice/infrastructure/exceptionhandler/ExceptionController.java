package com.example.shiftservice.infrastructure.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        ErrorModel model = new ErrorModel(HttpStatus.BAD_REQUEST, "Validation error",
                ex.getBindingResult().toString());

        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidRequest(InvalidRequestException ex) {
        ErrorModel model = new ErrorModel(HttpStatus.BAD_REQUEST, "Invalid request", ex.getMessage());

        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleException(Exception ex) {
        ErrorModel model = new ErrorModel(HttpStatus.NOT_FOUND, "Request not found",
                Arrays.toString(ex.getStackTrace()));

        return new ResponseEntity<>(model, HttpStatus.NOT_FOUND);
    }
}

