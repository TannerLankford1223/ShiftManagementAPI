package com.example.employeeservice.application.controller;

import com.example.employeeservice.domain.dto.EmployeeDTO;
import com.example.employeeservice.domain.ports.api.EmployeeServicePort;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeServicePort employeeService;

    public EmployeeController(EmployeeServicePort employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registers a new employee")
    public EmployeeDTO registerEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.registerEmployee(employeeDTO);
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "Returns employee, if they exist")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @GetMapping("/")
    @Operation(summary = "Returns a list of all employees")
    public List<EmployeeDTO> getEmployees() {
        return employeeService.getEmployees();
    }

    @PutMapping("/update/{employeeId}")
    @Operation(summary = "Updates an employee, if they exist")
    public EmployeeDTO updateEmployee(@PathVariable long employeeId, @Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(employeeId);
        return employeeService.updateEmployee(employeeDTO);
    }

    @DeleteMapping("/{employeeId}")
    @Operation(summary = "Deletes an employee, if they exist")
    public ResponseEntity<String> deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee with id " + employeeId + " deleted", HttpStatus.OK);
    }

    // Method created for communication with FeignClient in Shift Service API
    @GetMapping("/{employeeId}/check")
    @Operation(summary = "Returns a boolean indicating whether the employee exists")
    public boolean employeeExists(@PathVariable long employeeId) {
        return employeeService.employeeExists(employeeId);
    }
}
