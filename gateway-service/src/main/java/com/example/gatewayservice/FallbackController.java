package com.example.gatewayservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/address")
    public ResponseEntity<String> addressFallback() {
        return new ResponseEntity<>("Gateway was unable to connect to the address service",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/employee")
    public ResponseEntity<String> employeeFallback() {
        return new ResponseEntity<>("Gateway was unable to connect to the employee service",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/shift")
    public ResponseEntity<String> shiftFallback() {
        return new ResponseEntity<>("Gateway was unable to connect to the shift service",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
