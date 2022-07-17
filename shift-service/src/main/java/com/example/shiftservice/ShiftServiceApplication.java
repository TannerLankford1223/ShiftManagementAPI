package com.example.shiftservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Shift Service API", version = "1.0", description = "Documentation for the Shift Service API v1.0"))
public class ShiftServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiftServiceApplication.class, args);
    }

}
