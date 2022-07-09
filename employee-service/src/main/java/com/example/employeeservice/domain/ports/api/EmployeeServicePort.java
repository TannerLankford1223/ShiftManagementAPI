package com.example.employeeservice.domain.ports.api;

import com.example.employeeservice.domain.dto.EmployeeDTO;

public interface EmployeeServicePort {

    EmployeeDTO saveEmployee(EmployeeDTO employee);

    EmployeeDTO getEmployee(long employeeId);

    EmployeeDTO updateEmployee(EmployeeDTO employee);

    void deleteEmployee(long employeeId);

    boolean employeeExists(long employeeId);

}
