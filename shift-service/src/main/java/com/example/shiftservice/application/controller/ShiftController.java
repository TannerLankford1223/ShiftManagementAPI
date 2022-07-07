package com.example.shiftservice.application.controller;

import com.example.shiftservice.domain.dto.DailySchedule;
import com.example.shiftservice.domain.dto.ScheduleRequest;
import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Returns list of shifts for an employee for a given time period if the employee ID is specified in the
    // request body. Otherwise, it returns shifts for all employees.
    @GetMapping("/schedule")
    public List<DailySchedule> getWorkSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        return null;
    }

    // Posts a list of shifts for a given time period
    @PostMapping("/schedule")
    public ResponseEntity<String> postWorkSchedule(@RequestBody List<ShiftRequest> shiftRequests) {
        return null;
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<String> deleteEmployeeShift(@PathVariable long shiftId) {
        return null;
    }
}
