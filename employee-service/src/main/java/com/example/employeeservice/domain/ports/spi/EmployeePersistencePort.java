package com.example.employeeservice.domain.ports.spi;

import com.example.employeeservice.infrastructure.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeePersistencePort {

    Employee saveEmployee(Employee employee);

    Optional<Employee> getEmployee(long employeeId);

    List<Employee> getEmployees();

    void deleteEmployee(long employeeId);

    boolean employeeExists(long employeeId);
}
