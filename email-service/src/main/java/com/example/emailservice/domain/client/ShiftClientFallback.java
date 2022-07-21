package com.example.emailservice.domain.client;

import com.example.emailservice.domain.dto.EmployeeShiftsRequest;
import com.example.emailservice.domain.dto.Shift;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
@Slf4j
public class ShiftClientFallback implements ShiftClient {

    @Override
    public List<Shift> getWorkSchedule(@Valid EmployeeShiftsRequest request) {
        log.error("Unable to connect to shift-service");
        return null;
    }

    @Override
    public Shift getEmployeeShift(long shiftId) {
        log.error("Unable to connect to shift-service");
        return null;
    }
}
