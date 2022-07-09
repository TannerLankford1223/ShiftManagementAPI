package com.example.employeeservice.domain.ports.spi;

import com.example.employeeservice.infrastructure.entity.Employee;

import java.util.List;

public interface EmployeePersistencePort {

    Employee saveEmployee(Employee employee);

    Employee getEmployee(long employeeId);

    List<Employee> getEmployees();

    Employee deleteEmployee(long employeeId);

    boolean employeeExists(long employeeId);
}
