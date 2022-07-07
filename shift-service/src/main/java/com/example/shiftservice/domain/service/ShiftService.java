package com.example.shiftservice.domain.service;

import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import org.springframework.stereotype.Service;

@Service
public class ShiftService implements ShiftServicePort {

    private final ShiftPersistencePort shiftRepo;

    public ShiftService(ShiftPersistencePort shiftRepo) {
        this.shiftRepo = shiftRepo;
    }

    @Override
    public ShiftResponse createShift(ShiftRequest shiftRequest) {
        return null;
    }

    @Override
    public ShiftResponse getEmployeeShift(long shiftId) {
        return null;
    }

    @Override
    public boolean deleteEmployeeShift(long shiftId) {
        return false;
    }
}
