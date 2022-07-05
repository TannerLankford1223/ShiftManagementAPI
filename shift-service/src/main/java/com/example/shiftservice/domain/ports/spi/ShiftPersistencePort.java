package com.example.shiftservice.domain.ports.spi;

import com.example.shiftservice.infrastructure.entity.Shift;

import java.time.LocalDate;
import java.util.Optional;

public interface ShiftPersistencePort {

    Shift createShift(Shift shift);

    Optional<Shift> getShift(String employeeEmail, LocalDate shiftDate);

    Shift updateShift(Shift shift);

    Shift deleteShift(Shift shift);
}
