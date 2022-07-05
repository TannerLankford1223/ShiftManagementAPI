package com.example.shiftservice.domain.service;

import com.example.shiftservice.domain.dto.ShiftDTO;
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
    public ShiftDTO createShift(ShiftDTO shiftRequest) {
        return null;
    }

    @Override
    public ShiftDTO getEmployeeShift(String email) {
        return null;
    }

    @Override
    public ShiftDTO updateEmployeeShift(ShiftDTO shiftRequest) {
        return null;
    }

    @Override
    public boolean deleteEmployeeShift(long shiftId) {
        return false;
    }
}
