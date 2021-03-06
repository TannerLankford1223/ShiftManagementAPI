package com.example.shiftservice.domain.ports.spi;

import com.example.shiftservice.infrastructure.entity.Shift;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShiftPersistencePort {

    Shift saveShift(Shift shift);

    Optional<Shift> getShift(long shiftId);

    List<Shift> getWorkSchedule(long storeId, LocalDate startDate, LocalDate endDate);

    List<Shift> getEmployeeSchedule(long storeId, long employeeId, LocalDate startDate, LocalDate endDate);

    void postWorkSchedule(List<Shift> shift);

    void deleteShift(long shiftId);

    boolean shiftExists(long shiftId);
}
