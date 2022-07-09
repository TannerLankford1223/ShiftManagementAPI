package com.example.shiftservice.infrastructure.mapper;

import com.example.shiftservice.domain.dto.ShiftDTO;
import com.example.shiftservice.infrastructure.entity.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ShiftMapper {
    public ShiftDTO shiftToShiftDTO(Shift shift) {
        return new ShiftDTO(shift.getId(), shift.getEmployeeId(), shift.getShiftDate(),
                shift.getStartTime(), shift.getEndTime());
    }

    @Mapping(target = "shift.id", ignore = true)
    public Shift shiftDTOToShift(ShiftDTO request) {
        return Shift.builder()
                .employeeId(request.getEmployeeId())
                .shiftDate(request.getShiftDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }
}
