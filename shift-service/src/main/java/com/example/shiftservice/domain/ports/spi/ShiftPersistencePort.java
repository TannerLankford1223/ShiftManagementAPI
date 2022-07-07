package com.example.shiftservice.domain.ports.spi;

import com.example.shiftservice.infrastructure.entity.Shift;

import java.util.Optional;

public interface ShiftPersistencePort {

    Shift createShift(Shift shift);

    Optional<Shift> getShift(long shiftId);

    Shift deleteShift(long shiftId);
}
