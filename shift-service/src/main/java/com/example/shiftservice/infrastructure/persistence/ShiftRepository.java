package com.example.shiftservice.infrastructure.persistence;

import com.example.shiftservice.infrastructure.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    Optional<Shift> getShiftById(long shiftId);

    List<Shift> findAllByShiftDateBetween(LocalDate startDate, LocalDate endDate);

    List<Shift> findAllByEmployeeIdAndShiftDateBetween(long employeeId, LocalDate startDate, LocalDate endDate);

    Shift deleteShiftById(long shiftId);
}
