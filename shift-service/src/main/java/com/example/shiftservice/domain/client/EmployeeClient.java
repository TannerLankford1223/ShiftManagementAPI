package com.example.shiftservice.domain.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "employee-service", url = "http://localhost:8282", fallback = EmployeeFallback.class)
public interface EmployeeClient {

    @GetMapping("/api/employees/exists/{employeeId}")
    boolean employeeExists(@PathVariable long employeeId);
}

@Slf4j
class EmployeeFallback implements EmployeeClient {

    @Override
    public boolean employeeExists(long employeeId) {
        log.error("Unable to connect to employee service server");
        return false;
    }
}
