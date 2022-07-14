package com.example.employeeservice.application.controller;

import com.example.employeeservice.domain.dto.EmployeeDTO;
import com.example.employeeservice.domain.ports.api.EmployeeServicePort;
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
    public EmployeeDTO registerEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.registerEmployee(employeeDTO);
    }

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @GetMapping("/")
    public List<EmployeeDTO> getEmployees() {
        return employeeService.getEmployees();
    }

    @PutMapping("/update/{employeeId}")
    public EmployeeDTO updateEmployee(@PathVariable long employeeId, @Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(employeeId);
        return employeeService.updateEmployee(employeeDTO);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee with id " + employeeId + " deleted", HttpStatus.OK);
    }

    // Method created for communication with FeignClient in Shift Service API
    @GetMapping("/{employeeId}/check")
    public boolean employeeExists(@PathVariable long employeeId) {
        return employeeService.employeeExists(employeeId);
    }
}
