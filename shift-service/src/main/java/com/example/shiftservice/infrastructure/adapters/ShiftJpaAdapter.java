package com.example.shiftservice.infrastructure.adapters;

import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import com.example.shiftservice.infrastructure.entity.Shift;
import com.example.shiftservice.infrastructure.persistence.ShiftRepository;
import org.springframework.stereotype.Service;

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
        return shiftRepo.getShiftById(shiftId);
    }

    @Override
    public Shift deleteShift(long shiftId) {
        return shiftRepo.deleteShiftById(shiftId);
    }
}
