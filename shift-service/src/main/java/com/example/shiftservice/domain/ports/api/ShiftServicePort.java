package com.example.shiftservice.domain.ports.api;

import com.example.shiftservice.domain.dto.ScheduleRequest;
import com.example.shiftservice.domain.dto.ShiftDTO;

import java.util.List;

public interface ShiftServicePort {

    ShiftDTO saveShift(ShiftDTO shiftDTO);

    ShiftDTO getEmployeeShift(long shiftId);

    ShiftDTO updateEmployeeShift(ShiftDTO shiftUpdate);

    List<ShiftDTO> getWorkSchedule(ScheduleRequest scheduleRequest);

    List<ShiftDTO> getEmployeeSchedule(ScheduleRequest scheduleRequest);

    void postWorkSchedule(List<ShiftDTO> shiftDTOS);

    void deleteEmployeeShift(long shiftId);

    boolean isValidShiftRequest(ShiftDTO shiftDTO);
}
