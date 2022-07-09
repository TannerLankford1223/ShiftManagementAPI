package com.example.employeeservice.domain.ports.spi;

import com.example.employeeservice.infrastructure.entity.Employee;

public interface EmployeePersistencePort {

    Employee saveEmployee(Employee employee);

    Employee getEmployee(long employeeId);

    Employee deleteEmployee(long employeeId);

    boolean employeeExists(long employeeId);
}
