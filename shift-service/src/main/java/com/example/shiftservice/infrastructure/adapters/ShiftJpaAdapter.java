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
        return null;
    }

    @Override
    public Optional<Shift> getShift(long shiftId) {
        return Optional.empty();
    }

    @Override
    public Shift deleteShift(long shiftId) {
        return null;
    }
}
