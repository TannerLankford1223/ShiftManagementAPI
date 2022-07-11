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

import javax.transaction.Transactional;
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

    @Transactional
    @Override
    public ShiftDTO createShift(ShiftDTO shiftDTO) {
        if (isValidShiftRequest(shiftDTO)) {
            Shift shift = mapper.shiftDTOToShift(shiftDTO);
            return mapper.shiftToShiftDTO(shiftRepo.createShift(shift));
        } else {
            throw new InvalidRequestException("Invalid shift request");
        }
    }

    @Override
    public ShiftDTO getEmployeeShift(long shiftId) {
        Optional<Shift> shiftOpt = shiftRepo.getShift(shiftId);

        if (shiftOpt.isPresent()) {
            return mapper.shiftToShiftDTO(shiftOpt.get());
        }
        log.error("Unable to find shift with id " + shiftId);
        throw new ShiftNotFoundException(shiftId);
    }

    @Override
    public List<DailySchedule> getWorkSchedule(ScheduleRequest scheduleRequest) {
        if (scheduleRequest.getEndDate().isBefore(scheduleRequest.getStartDate())) {
            throw new InvalidRequestException("The end date of the time period must be on or after the start date");
        }

        List<Shift> shifts = shiftRepo.getWorkSchedule(scheduleRequest.getStoreId(), scheduleRequest.getStartDate(),
                scheduleRequest.getEndDate());

        return toDailyScheduleList(shifts);
    }

    @Override
    public List<DailySchedule> getEmployeeSchedule(ScheduleRequest scheduleRequest) {
        if (!employeeClient.employeeExists(scheduleRequest.getEmployeeId())) {
            log.error("Employee with id " + scheduleRequest.getEmployeeId() + " does not exist");
            throw new InvalidRequestException("Employee with id " + scheduleRequest.getEmployeeId() + " does not exist");
        } else if (scheduleRequest.getEndDate().isBefore(scheduleRequest.getStartDate())) {
            throw new InvalidRequestException("The end date of the time period must be on or after the start date");
        }

        List<Shift> shifts = shiftRepo.getEmployeeSchedule(scheduleRequest.getStoreId(), scheduleRequest.getEmployeeId(),
                scheduleRequest.getStartDate(), scheduleRequest.getEndDate());

        return toDailyScheduleList(shifts);
    }

    private List<DailySchedule> toDailyScheduleList(List<Shift> shifts) {
        Map<LocalDate, List<Shift>> groupedShifts =
                shifts.stream().collect(Collectors.groupingBy(Shift::getShiftDate));

        return groupedShifts.entrySet()
                .stream()
                .map(e -> {
                    List<ShiftDTO> shiftResponses = new ArrayList<>(e.getValue()
                            .stream()
                            .map(mapper::shiftToShiftDTO)
                            .toList());

                    return new DailySchedule(e.getKey(), shiftResponses);
                })
                .sorted(Comparator.comparing(DailySchedule::getDate))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void postWorkSchedule(List<ShiftDTO> shiftDTOS) {
        List<Shift> shifts = new ArrayList<>();

        for (ShiftDTO request : shiftDTOS) {
            if (isValidShiftRequest(request)) {
                shifts.add(mapper.shiftDTOToShift(request));
            } else {
                throw new InvalidRequestException("Shift request invalid");
            }
        }

        shiftRepo.postWorkSchedule(shifts);
    }

    @Transactional
    @Override
    public void deleteEmployeeShift(long shiftId) {

        if (shiftRepo.shiftExists(shiftId)) {
            shiftRepo.deleteShift(shiftId);
        } else {
            log.error("Shift with id " + shiftId + " not found");
            throw new InvalidRequestException("Shift with id " + shiftId + " not found");
        }
    }

    @Override
    public boolean isValidShiftRequest(ShiftDTO shiftDTO) {
        if (!employeeClient.employeeExists(shiftDTO.getEmployeeId())) {
            log.error("Employee with id " + shiftDTO.getEmployeeId() + " not found");
            return false;
        } else if (shiftDTO.getShiftDate().isBefore(LocalDate.now())) {
            log.error("Invalid date: date must be on or after " + LocalDate.now());
            return false;
        } else if (shiftDTO.getStartTime().isBefore(LocalTime.parse(START_TIME))
                || shiftDTO.getEndTime().isAfter(LocalTime.parse(END_TIME))) {
            log.error("Employee shift times need to start on or after "
                    + LocalTime.parse(START_TIME) + " (8:00AM) and end on or before " + LocalTime.parse(END_TIME)
                    + " (5:00PM)");
            return false;
        }

        return true;
    }
}
