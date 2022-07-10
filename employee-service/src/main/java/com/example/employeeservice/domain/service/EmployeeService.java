package com.example.employeeservice.domain.service;

import com.example.employeeservice.domain.dto.EmployeeDTO;
import com.example.employeeservice.domain.ports.api.EmployeeServicePort;
import com.example.employeeservice.domain.ports.spi.EmployeePersistencePort;
import com.example.employeeservice.infrastructure.entity.Employee;
import com.example.employeeservice.infrastructure.exceptionhandler.InvalidRequestException;
import com.example.employeeservice.infrastructure.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService implements EmployeeServicePort {

    private final EmployeePersistencePort employeeRepo;

    private final EmployeeMapper mapper;

    public EmployeeService(EmployeePersistencePort employeeRepo, EmployeeMapper mapper) {
        this.employeeRepo = employeeRepo;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public EmployeeDTO registerEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapper.employeeDTOToEmployee(employeeDTO);
        return mapper.employeeToEmployeeDTO(employeeRepo.saveEmployee(employee));
    }

    @Override
    public EmployeeDTO getEmployee(long employeeId) {
        Optional<Employee> employeeOpt = employeeRepo.getEmployee(employeeId);

        if (employeeOpt.isPresent()) {
            return mapper.employeeToEmployeeDTO(employeeOpt.get());
        } else {
            log.error("Employee with id " + employeeId + " not found");
            throw new InvalidRequestException("User with id " + employeeId + " not found");
        }
    }

    @Override
    public List<EmployeeDTO> getEmployees() {
        List<Employee> employees = employeeRepo.getEmployees();

        return employees.stream()
                .map(mapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOpt = employeeRepo.getEmployee(employeeDTO.getId());

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setEmail(employeeDTO.getEmail());
            employee.setPhoneNumber(employeeDTO.getPhoneNumber());

            return mapper.employeeToEmployeeDTO(employeeRepo.saveEmployee(employee));
        }

        log.error("Employee with id " + employeeDTO.getId() + " not found");
        throw new InvalidRequestException("Employee with id " + employeeDTO.getId() + " not found");
    }

    @Transactional
    @Override
    public void deleteEmployee(long employeeId) {
        if (employeeExists(employeeId)) {
            employeeRepo.deleteEmployee(employeeId);
        } else {
            log.error("Employee with id " + employeeId + " not found");
            throw new InvalidRequestException("Employee with id " + employeeId + " not found");
        }
    }

    @Override
    public boolean employeeExists(long employeeId) {
        return employeeRepo.employeeExists(employeeId);
    }
}
