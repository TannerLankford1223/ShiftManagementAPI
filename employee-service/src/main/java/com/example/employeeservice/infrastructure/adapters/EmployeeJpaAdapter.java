package com.example.employeeservice.infrastructure.adapters;

import com.example.employeeservice.domain.ports.spi.EmployeePersistencePort;
import com.example.employeeservice.infrastructure.entity.Employee;
import com.example.employeeservice.infrastructure.persistence.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeJpaAdapter implements EmployeePersistencePort {

    private final EmployeeRepository employeeRepo;

    public EmployeeJpaAdapter(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee getEmployee(long employeeId) {
        return null;
    }

    @Override
    public List<Employee> getEmployees() {
        return null;
    }

    @Override
    public Employee deleteEmployee(long employeeId) {
        return null;
    }

    @Override
    public boolean employeeExists(long employeeId) {
        return false;
    }
}
