package com.example.emailservice.domain.client;

import com.example.emailservice.domain.dto.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeClientFallback implements EmployeeClient {

    @Override
    public Employee getEmployee(long employeeId) {
        log.error("Unable to connect to employee-service");
        return null;
    }
}
