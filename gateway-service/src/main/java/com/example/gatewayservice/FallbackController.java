package com.example.gatewayservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/address")
    public String productsFallback() {
        return "Unable to connect to address service";
    }

    @GetMapping("/employee")
    public String ordersFallback() {
        return "Unable to connect to employee service";
    }

    @GetMapping("/shift")
    public String customersFallback() {
        return "Unable to connect to shift service";
    }

    @GetMapping("/email")
    public String paymentsFallback() {
        return "Unable to connect to email service";
    }
}
