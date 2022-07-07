package com.example.shiftservice.domain.client;

import com.example.shiftservice.domain.dto.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service", url = "http://localhost:8282", fallback = EmployeeFallback.class)
public interface EmployeeClient {

    @GetMapping("/api/employees/{employeeId}")
    Employee getEmployee(@PathVariable long employeeId);
}

class EmployeeFallback implements EmployeeClient {

    @Override
    public Employee getEmployee(long employeeId) {
        return null;
    }
}
