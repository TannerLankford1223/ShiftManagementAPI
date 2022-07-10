package com.example.shiftservice.infrastructure.adapters;

import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import com.example.shiftservice.infrastructure.entity.Shift;
import com.example.shiftservice.infrastructure.persistence.ShiftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftJpaAdapter implements ShiftPersistencePort {

    private final ShiftRepository shiftRepo;

    public ShiftJpaAdapter(ShiftRepository shiftRepo) {
        this.shiftRepo = shiftRepo;
    }

    @Override
    public Shift createShift(Shift shift) {
        return shiftRepo.save(shift);
    }

    @Override
    public Optional<Shift> getShift(long shiftId) {
        return shiftRepo.findById(shiftId);
    }

    @Override
    public List<Shift> getWorkSchedule(LocalDate startDate, LocalDate endDate) {
        return shiftRepo.findAllByShiftDateBetween(startDate, endDate);
    }

    @Override
    public List<Shift> getEmployeeSchedule(long employeeId, LocalDate startDate, LocalDate endDate) {
        return shiftRepo.findAllByEmployeeIdAndShiftDateBetween(employeeId, startDate, endDate);
    }

    @Override
    public void postWorkSchedule(List<Shift> shifts) {
        shiftRepo.saveAll(shifts);
    }

    @Override
    public Shift deleteShift(long shiftId) {
        return shiftRepo.deleteShiftById(shiftId);
    }
}
