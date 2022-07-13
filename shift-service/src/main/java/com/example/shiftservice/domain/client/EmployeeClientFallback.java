package com.example.shiftservice.domain.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeClientFallback implements EmployeeClient {

    @Override
    public boolean employeeExists(long employeeId) {
        log.error("Unable to connect to employee-service");
        return false;
    }
}
