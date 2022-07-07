package com.example.shiftservice.infrastructure.mapper;

import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;
import com.example.shiftservice.infrastructure.entity.Shift;
import org.mapstruct.Mapper;

@Mapper
public interface ShiftMapper {

    ShiftResponse shiftToShiftResponse(Shift shift);

    Shift shiftRequestToShift(ShiftRequest shiftRequest);
}
