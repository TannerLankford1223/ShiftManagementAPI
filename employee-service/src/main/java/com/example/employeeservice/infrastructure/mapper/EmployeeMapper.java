package com.example.employeeservice.infrastructure.mapper;

import com.example.employeeservice.domain.dto.EmployeeDTO;
import com.example.employeeservice.infrastructure.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO employeeToEmployeeDTO(Employee employee);
}
