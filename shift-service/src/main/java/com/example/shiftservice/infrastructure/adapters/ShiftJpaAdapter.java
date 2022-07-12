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
    public List<Shift> getWorkSchedule(long storeId, LocalDate startDate, LocalDate endDate) {
        return shiftRepo.findAllByStoreIdAndShiftDateBetween(storeId, startDate, endDate);
    }

    @Override
    public List<Shift> getEmployeeSchedule(long storeId, long employeeId, LocalDate startDate, LocalDate endDate) {
        return shiftRepo.findAllByStoreIdAndEmployeeIdAndShiftDateBetween(storeId, employeeId, startDate, endDate);
    }

    @Override
    public void postWorkSchedule(List<Shift> shifts) {
        shiftRepo.saveAll(shifts);
    }

    @Override
    public void deleteShift(long shiftId) {
        shiftRepo.deleteShiftById(shiftId);
    }

    @Override
    public boolean shiftExists(long shiftId) {
        return shiftRepo.existsById(shiftId);
    }
}
