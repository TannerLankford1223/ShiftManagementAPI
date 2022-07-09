package com.example.employeeservice.application.controller;

import com.example.employeeservice.domain.dto.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @PostMapping("/new-employee")
    public EmployeeDTO registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return null;
    }

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return null;
    }

    @PutMapping("/update")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return null;
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long employeeId) {
        return null;
    }

    // Method created for communication with FeignClient in Shift Service API
    @GetMapping("/exists/{employeeId}")
    public boolean employeeExists(@PathVariable long employeeId) {
        return false;
    }
}
