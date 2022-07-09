package com.example.employeeservice.domain.service;

import com.example.employeeservice.domain.dto.EmployeeDTO;
import com.example.employeeservice.domain.ports.api.EmployeeServicePort;
import com.example.employeeservice.domain.ports.spi.EmployeePersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeService implements EmployeeServicePort {

    private final EmployeePersistencePort employeeRepo;

    public EmployeeService(EmployeePersistencePort employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public EmployeeDTO registerEmployee(EmployeeDTO employee) {
        return null;
    }

    @Override
    public EmployeeDTO getEmployee(long employeeId) {
        return null;
    }

    @Override
    public List<EmployeeDTO> getEmployees() {
        return null;
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employee) {
        return null;
    }

    @Override
    public void deleteEmployee(long employeeId) {

    }

    @Override
    public boolean employeeExists(long employeeId) {
        return false;
    }
}
