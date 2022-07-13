package com.example.shiftservice.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "employee-service", fallback = EmployeeClientFallback.class)
public interface EmployeeClient {

    @GetMapping("/api/employee/{employeeId}/check")
    boolean employeeExists(@PathVariable long employeeId);
}
