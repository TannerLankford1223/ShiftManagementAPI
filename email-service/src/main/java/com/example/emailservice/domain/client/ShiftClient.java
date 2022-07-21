package com.example.emailservice.domain.client;

import com.example.emailservice.domain.dto.EmployeeShiftsRequest;
import com.example.emailservice.domain.dto.Shift;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "shift-service", fallback = ShiftClientFallback.class)
public interface ShiftClient {

    @PostMapping("/api/v1/shift/schedule")
    List<Shift> getWorkSchedule(@Valid EmployeeShiftsRequest request);

    @GetMapping("/api/v1/shift/{shiftId}")
    Shift getEmployeeShift(@PathVariable long shiftId);
}
