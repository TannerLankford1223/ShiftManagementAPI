package com.example.shiftservice.domain.ports.api;

import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;

public interface ShiftServicePort {

    ShiftResponse createShift(ShiftRequest shiftRequest);

    ShiftResponse getEmployeeShift(long shiftId);

    boolean deleteEmployeeShift(long shiftId);
}
