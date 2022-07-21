package com.example.emailservice.application.controller;

import com.example.emailservice.domain.dto.StoreScheduleRequest;
import com.example.emailservice.domain.ports.api.EmailServicePort;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailServicePort emailService;

    public EmailController(EmailServicePort emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-schedule")
    @Operation(summary = "Retrieves a list of shifts for a store in a seven day time period and emails each employee " +
            "that is working a list of their shifts")
    public ResponseEntity<String> sendStoreSchedule(@RequestBody StoreScheduleRequest request) throws Exception {
       String response = emailService.sendWeeklySchedule(request.getStoreId(), request.getStartOfWeekDate());
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/send-update/{shiftId}")
    @Operation(summary = "Emails an employee about an update in their shift with shift ID. The type of update " +
            "(cancellation/update) is specified by the action string provided as an argument")
    public ResponseEntity<String> updateEmployeeSchedule(@PathVariable long shiftId, @RequestBody String action) throws Exception {
        return new ResponseEntity<>(emailService.updateEmployeeSchedule(shiftId, action), HttpStatus.OK);
    }

}
