package com.example.employeeservice.domain.ports.api;

import com.example.employeeservice.domain.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeServicePort {

    EmployeeDTO saveEmployee(EmployeeDTO employee);

    EmployeeDTO getEmployee(long employeeId);

    List<EmployeeDTO> getEmployees();

    EmployeeDTO updateEmployee(EmployeeDTO employee);

    void deleteEmployee(long employeeId);

    boolean employeeExists(long employeeId);

}
