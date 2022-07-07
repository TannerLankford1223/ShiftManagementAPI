package com.example.shiftservice.domain.ports.api;

import com.example.shiftservice.domain.dto.DailySchedule;
import com.example.shiftservice.domain.dto.ScheduleRequest;
import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;

import java.util.List;

public interface ShiftServicePort {

    ShiftResponse createShift(ShiftRequest shiftRequest);

    ShiftResponse getEmployeeShift(long shiftId);

    List<DailySchedule> getWorkSchedule(ScheduleRequest scheduleRequest);

    List<DailySchedule> getEmployeeSchedule(ScheduleRequest scheduleRequest);

    void postWorkSchedule(List<ShiftRequest> shiftRequests);

    boolean deleteEmployeeShift(long shiftId);
}
