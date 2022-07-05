package com.example.shiftservice.application.controller;

import com.example.shiftservice.domain.dto.ShiftDTO;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shift")
public class ShiftController {

    private final ShiftServicePort shiftService;

    public ShiftController(ShiftServicePort shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping("/new-shift")
    public ShiftDTO createShift(@RequestBody ShiftDTO shift) {
        return null;
    }

    @GetMapping("/{email}")
    public ShiftDTO getEmployeeShift(@PathVariable String email) {
        return null;
    }

    @PutMapping("/update-shift")
    public ShiftDTO updateEmployeeShift(@RequestBody ShiftDTO shift) {
        return null;
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<String> deleteEmployeeShift(@PathVariable long shiftId) {
        return null;
    }
}
