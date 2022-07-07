package com.example.shiftservice.application.controller;

import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;
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
    public ShiftResponse createShift(@RequestBody ShiftRequest shift) {
        return null;
    }

    @GetMapping("/{email}")
    public ShiftResponse getEmployeeShift(@PathVariable String email) {
        return null;
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<String> deleteEmployeeShift(@PathVariable long shiftId) {
        return null;
    }
}
