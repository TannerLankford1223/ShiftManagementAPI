package com.example.shiftservice.infrastructure.persistence;

import com.example.shiftservice.infrastructure.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<Shift> findAllByStoreIdAndShiftDateBetween(long storeId, LocalDate startDate, LocalDate endDate);

    List<Shift> findAllByStoreIdAndEmployeeIdAndShiftDateBetween(long storeId, long employeeId,
                                                                 LocalDate startDate, LocalDate endDate);

    void deleteShiftById(long shiftId);
}
