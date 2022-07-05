package com.example.shiftservice.domain.ports.api;

import com.example.shiftservice.domain.dto.ShiftDTO;

public interface ShiftServicePort {

    ShiftDTO createShift(ShiftDTO shiftRequest);

    ShiftDTO getEmployeeShift(ShiftDTO shiftDTO);

    boolean deleteEmployeeShift(long shiftId);
}
