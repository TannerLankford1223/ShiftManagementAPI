package com.example.employeeservice.application.controller;

import com.example.employeeservice.domain.dto.EmployeeDTO;
import com.example.employeeservice.domain.ports.api.EmployeeServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeServicePort employeeService;

    public EmployeeController(EmployeeServicePort employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public EmployeeDTO registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
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
    public EmployeeDTO updateEmployee(@PathVariable long employeeId, @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(employeeId);
        return employeeService.updateEmployee(employeeDTO);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee with id " + employeeId + " deleted", HttpStatus.OK);
    }

    // Method created for communication with FeignClient in Shift Service API
    @GetMapping("/exists/{employeeId}")
    public boolean employeeExists(@PathVariable long employeeId) {
        return employeeService.employeeExists(employeeId);
    }
}
