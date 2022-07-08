package com.example.shiftservice.domain.service;

import com.example.shiftservice.domain.client.EmployeeClient;
import com.example.shiftservice.domain.dto.*;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import com.example.shiftservice.infrastructure.entity.Shift;
import com.example.shiftservice.infrastructure.exceptionhandler.InvalidRequestException;
import com.example.shiftservice.infrastructure.exceptionhandler.ShiftNotFoundException;
import com.example.shiftservice.infrastructure.mapper.ShiftMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShiftService implements ShiftServicePort {

    private final String START_TIME = "08:00";

    private final String END_TIME = "17:00";

    private final ShiftPersistencePort shiftRepo;

    private final EmployeeClient employeeClient;
    private final ShiftMapper mapper;


    public ShiftService(ShiftPersistencePort shiftRepo, EmployeeClient employeeClient, ShiftMapper mapper) {
        this.shiftRepo = shiftRepo;
        this.employeeClient = employeeClient;
        this.mapper = mapper;
    }

    @Override
    public ShiftResponse createShift(ShiftRequest shiftRequest) {
        if (isValidShiftRequest(shiftRequest)) {
            Shift shift = mapper.shiftRequestToShift(shiftRequest);
            return mapper.shiftToShiftResponse(shiftRepo.createShift(shift));
        } else {
            throw new InvalidRequestException("Invalid shift request");
        }
    }

    @Override
    public ShiftResponse getEmployeeShift(long shiftId) {
        Optional<Shift> shiftOpt = shiftRepo.getShift(shiftId);

        if (shiftOpt.isPresent()) {
            return mapper.shiftToShiftResponse(shiftOpt.get());
        }
        log.warn("Unable to find shift with id " + shiftId);
        throw new ShiftNotFoundException(shiftId);
    }

    @Override
    public List<DailySchedule> getWorkSchedule(ScheduleRequest scheduleRequest) {
        if (scheduleRequest.getEndDate().isBefore(scheduleRequest.getStartDate())) {
            throw new InvalidRequestException("The end date of the time period must be on or after the start date");
        }

        List<Shift> shifts = shiftRepo.getWorkSchedule(scheduleRequest.getStartDate(), scheduleRequest.getEndDate());

        return toDailyScheduleList(shifts);
    }

    @Override
    public List<DailySchedule> getEmployeeSchedule(ScheduleRequest scheduleRequest) {
        if (!employeeClient.employeeExists(scheduleRequest.getEmployeeId())) {
            log.warn("Employee with id " + scheduleRequest.getEmployeeId() + " does not exist");
            throw new InvalidRequestException("Employee with id " + scheduleRequest.getEmployeeId() +  " does not exist");
        } else if (scheduleRequest.getEndDate().isBefore(scheduleRequest.getStartDate())) {
            throw new InvalidRequestException("The end date of the time period must be on or after the start date");
        }

        List<Shift> shifts = shiftRepo.getEmployeeSchedule(scheduleRequest.getEmployeeId(),
                scheduleRequest.getStartDate(), scheduleRequest.getEndDate());

        return toDailyScheduleList(shifts);
    }

    private List<DailySchedule> toDailyScheduleList(List<Shift> shifts) {
        Map<LocalDate, List<Shift>> groupedShifts =
                shifts.stream().collect(Collectors.groupingBy(Shift::getShiftDate));

        return groupedShifts.entrySet()
                .stream()
                .map(e -> {
                    List<ShiftResponse> shiftResponses = new ArrayList<>(e.getValue()
                            .stream()
                            .map(mapper::shiftToShiftResponse)
                            .toList());

                    return new DailySchedule(e.getKey(), shiftResponses);
                })
                .sorted(Comparator.comparing(DailySchedule::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public void postWorkSchedule(List<ShiftRequest> shiftRequests) {
        List<Shift> shifts = new ArrayList<>();

        for (ShiftRequest request : shiftRequests) {
            if (isValidShiftRequest(request)) {
                shifts.add(mapper.shiftRequestToShift(request));
            } else {
                throw new InvalidRequestException("Shift request invalid");
            }
        }

        shiftRepo.postWorkSchedule(shifts);
    }

    @Override
    public boolean deleteEmployeeShift(long shiftId) {
        Shift deletedShift = shiftRepo.deleteShift(shiftId);

        return deletedShift != null;
    }

    @Override
    public boolean isValidShiftRequest(ShiftRequest shiftRequest) {
        if (!employeeClient.employeeExists(shiftRequest.getEmployeeId())) {
            log.warn("Employee with id " + shiftRequest.getEmployeeId() + " not found");
            return false;
        } else if (shiftRequest.getShiftDate().isBefore(LocalDate.now())) {
            log.warn("Invalid date: date must be on or after " + LocalDate.now());
            return false;
        } else if (shiftRequest.getStartTime().isBefore(LocalTime.parse(START_TIME))
                || shiftRequest.getEndTime().isAfter(LocalTime.parse(END_TIME))) {
            log.warn("Employee shift times need to start on or after "
                    + LocalTime.parse(START_TIME) + " (8:00AM) and end on or before " + LocalTime.parse(END_TIME)
                    + " (5:00PM)");
            return false;
        }

        return true;
    }
}
