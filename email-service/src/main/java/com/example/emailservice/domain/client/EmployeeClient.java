package com.example.emailservice.domain.client;

import com.example.emailservice.domain.dto.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service", fallback = EmployeeClientFallback.class)
public interface EmployeeClient {

    @GetMapping("/api/v1/employee/{employeeId}")
    Employee getEmployee(@PathVariable long employeeId);
}
