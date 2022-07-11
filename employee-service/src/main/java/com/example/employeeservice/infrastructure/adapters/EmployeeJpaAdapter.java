package com.example.employeeservice.infrastructure.adapters;

import com.example.employeeservice.domain.ports.spi.EmployeePersistencePort;
import com.example.employeeservice.infrastructure.entity.Employee;
import com.example.employeeservice.infrastructure.persistence.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeJpaAdapter implements EmployeePersistencePort {

    private final EmployeeRepository employeeRepo;

    public EmployeeJpaAdapter(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public Optional<Employee> getEmployee(long employeeId) {
        return employeeRepo.findById(employeeId);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public void deleteEmployee(long employeeId) {
        employeeRepo.deleteById(employeeId);
    }

    @Override
    public boolean employeeExists(long employeeId) {
        return employeeRepo.existsById(employeeId);
    }
}
