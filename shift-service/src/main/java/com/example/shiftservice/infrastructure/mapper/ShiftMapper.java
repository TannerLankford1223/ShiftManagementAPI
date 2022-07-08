package com.example.shiftservice.infrastructure.mapper;

import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;
import com.example.shiftservice.infrastructure.entity.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ShiftMapper {
    public ShiftResponse shiftToShiftResponse(Shift shift) {
        return new ShiftResponse(shift.getId(), shift.getEmployeeId(), shift.getShiftDate(),
                shift.getStartTime(), shift.getEndTime());
    }

    @Mapping(target = "shift.id", ignore = true)
    public Shift shiftRequestToShift(ShiftRequest request) {
        return Shift.builder()
                .employeeId(request.getEmployeeId())
                .shiftDate(request.getShiftDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }
}
