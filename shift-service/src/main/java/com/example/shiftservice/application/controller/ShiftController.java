package com.example.shiftservice.application.controller;

import com.example.shiftservice.domain.dto.ScheduleRequest;
import com.example.shiftservice.domain.dto.ShiftDTO;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shift")
public class ShiftController {

    private final ShiftServicePort shiftService;

    public ShiftController(ShiftServicePort shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping("/new-shift")
    public ShiftDTO createShift(@Valid @RequestBody ShiftDTO shift) {
        return shiftService.createShift(shift);
    }

    @GetMapping("/{shiftId}")
    public ShiftDTO getEmployeeShift(@PathVariable long shiftId) {
        return shiftService.getEmployeeShift(shiftId);
    }

    // Returns list of shifts for an employee for a given time period if the employee ID is specified in the
    // request body. Otherwise, it returns shifts for all employees.
    @GetMapping("/schedule")
    public List<ShiftDTO> getWorkSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        if (scheduleRequest.getEmployeeId() == 0) {
            return shiftService.getWorkSchedule(scheduleRequest);
        } else {
            return shiftService.getEmployeeSchedule(scheduleRequest);
        }
    }

    // Posts a list of shifts for a given time period
    @PostMapping("/schedule")
    public ResponseEntity<String> postWorkSchedule(@RequestBody List<@Valid ShiftDTO> shiftDTOS) {
        shiftService.postWorkSchedule(shiftDTOS);
        return new ResponseEntity<>("Work schedule successfully posted", HttpStatus.OK);
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<String> deleteEmployeeShift(@PathVariable long shiftId) {
        shiftService.deleteEmployeeShift(shiftId);
        return new ResponseEntity<>("Shift successfully deleted", HttpStatus.OK);
    }
}
