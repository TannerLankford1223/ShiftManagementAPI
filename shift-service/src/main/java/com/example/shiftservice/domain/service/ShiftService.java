package com.example.shiftservice.domain.service;

import com.example.shiftservice.domain.client.EmployeeClient;
import com.example.shiftservice.domain.dto.*;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import com.example.shiftservice.infrastructure.exceptionhandler.InvalidRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ShiftService implements ShiftServicePort {

    @Value("${company.start_time}")
    private String companyStartTime;

    @Value("${company.end_time}")
    private String companyEndTime;

    private final ShiftPersistencePort shiftRepo;

    private final EmployeeClient employeeClient;


    public ShiftService(ShiftPersistencePort shiftRepo, EmployeeClient employeeClient) {
        this.shiftRepo = shiftRepo;
        this.employeeClient = employeeClient;
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
    public List<DailySchedule> getWorkSchedule(ScheduleRequest scheduleRequest) {
        return null;
    }

    @Override
    public List<DailySchedule> getEmployeeSchedule(ScheduleRequest scheduleRequest) {
        return null;
    }

    @Override
    public void postWorkSchedule(List<ShiftRequest> shiftRequests) {

    }

    @Override
    public boolean deleteEmployeeShift(long shiftId) {
        return false;
    }

    @Override
    public boolean isValidShiftRequest(ShiftRequest shiftRequest) {
        Employee employee = employeeClient.getEmployee(shiftRequest.getEmployeeId());
        if (employee == null) {
            throw new InvalidRequestException("Employee not found");
        } else if (shiftRequest.getShiftDate().isBefore(LocalDate.now())) {
            throw new InvalidRequestException("Invalid date: date must be on or after " + LocalDate.now());
        } else if (shiftRequest.getStartTime().isBefore(LocalTime.parse(companyStartTime))
                || shiftRequest.getEndTime().isAfter(LocalTime.parse(companyEndTime))) {
            throw new InvalidRequestException("Employee shift times need to start on or after "
                    + LocalTime.parse(companyStartTime) + " and end on or before " + LocalTime.parse(companyEndTime));
        }

        return true;
    }
}
