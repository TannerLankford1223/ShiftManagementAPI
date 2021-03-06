package com.example.shiftservice.domain.service;

import com.example.shiftservice.domain.client.AddressClient;
import com.example.shiftservice.domain.client.EmployeeClient;
import com.example.shiftservice.domain.dto.ScheduleRequest;
import com.example.shiftservice.domain.dto.ShiftDTO;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import com.example.shiftservice.infrastructure.entity.Shift;
import com.example.shiftservice.infrastructure.exceptionhandler.InvalidRequestException;
import com.example.shiftservice.infrastructure.mapper.ShiftMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShiftService implements ShiftServicePort {

    private final String START_TIME = "08:00";

    private final String END_TIME = "17:00";

    private final ShiftPersistencePort shiftRepo;

    private final EmployeeClient employeeClient;
    private final AddressClient addressClient;
    private final ShiftMapper mapper;


    @Transactional
    @Override
    public ShiftDTO saveShift(ShiftDTO shiftDTO) {
        if (isValidShiftRequest(shiftDTO)) {
            Shift shift = mapper.shiftDTOToShift(shiftDTO);
            ShiftDTO returnShiftDTO = mapper.shiftToShiftDTO(shiftRepo.saveShift(shift));
            log.info("Shift with id " + returnShiftDTO.getShiftId() + " saved");
            return returnShiftDTO;
        } else {
            throw new InvalidRequestException("Invalid shift request");
        }
    }

    @Override
    @Cacheable(cacheNames = "shifts")
    public ShiftDTO getEmployeeShift(long shiftId) {
        Optional<Shift> shiftOpt = shiftRepo.getShift(shiftId);

        if (shiftOpt.isPresent()) {
            return mapper.shiftToShiftDTO(shiftOpt.get());
        }

        throw new InvalidRequestException("Shift with id " + shiftId + " not found");
    }

    @Transactional
    @Override
    @CachePut(cacheNames = "shifts", key = "#shiftUpdate.shiftId")
    @CacheEvict(cacheNames = "workSchedules", allEntries = true)
    public ShiftDTO updateEmployeeShift(ShiftDTO shiftUpdate) {
        Optional<Shift> shiftOpt = shiftRepo.getShift(shiftUpdate.getShiftId());

        if (shiftOpt.isPresent()) {
            Shift shift = shiftOpt.get();
            shift.setEmployeeId(shiftUpdate.getEmployeeId());
            shift.setShiftDate(shiftUpdate.getShiftDate());
            shift.setStartTime(shiftUpdate.getStartTime());
            shift.setEndTime(shiftUpdate.getEndTime());
            log.info("Shift with id " + shift.getStoreId() + " updated");
            return mapper.shiftToShiftDTO(shiftRepo.saveShift(shift));
        }

        throw new InvalidRequestException("Shift with id " + shiftUpdate.getShiftId() + " not found");
    }

    @Override
    @Cacheable(cacheNames = "workSchedules")
    public List<ShiftDTO> getWorkSchedule(ScheduleRequest scheduleRequest) {
        if (scheduleRequest.getEndDate().isBefore(scheduleRequest.getStartDate())) {
            throw new InvalidRequestException("The end date of the time period must be on or after the start date");
        }

        List<Shift> shifts = shiftRepo.getWorkSchedule(scheduleRequest.getStoreId(), scheduleRequest.getStartDate(),
                scheduleRequest.getEndDate());

        return shifts.stream()
                .map(mapper::shiftToShiftDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftDTO> getEmployeeSchedule(ScheduleRequest scheduleRequest) {
        if (!employeeClient.employeeExists(scheduleRequest.getEmployeeId())) {
            throw new InvalidRequestException("Employee with id " + scheduleRequest.getEmployeeId() + " not found");
        } else if (scheduleRequest.getEndDate().isBefore(scheduleRequest.getStartDate())) {
            throw new InvalidRequestException("The end date of the time period must be on or after the start date");
        } else if (!addressClient.addressExists(scheduleRequest.getStoreId())) {
            throw new InvalidRequestException("Store with id " + scheduleRequest.getStoreId() + " not found");
        }

        List<Shift> shifts = shiftRepo.getEmployeeSchedule(scheduleRequest.getStoreId(), scheduleRequest.getEmployeeId(),
                scheduleRequest.getStartDate(), scheduleRequest.getEndDate());

        return shifts.stream()
                .map(mapper::shiftToShiftDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = "workSchedules", allEntries = true)
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
    @Caching(evict = {
            @CacheEvict(cacheNames = "workSchedules", allEntries = true),
            @CacheEvict(cacheNames = "shifts", key = "#shiftId")
    })
    public void deleteEmployeeShift(long shiftId) {

        if (shiftRepo.shiftExists(shiftId)) {
            shiftRepo.deleteShift(shiftId);
            log.info("Shift with id " + shiftId + " deleted");
        } else {
            throw new InvalidRequestException("Shift with id " + shiftId + " not found");
        }
    }

    @Override
    public boolean isValidShiftRequest(ShiftDTO shiftDTO) {
        if (!employeeClient.employeeExists(shiftDTO.getEmployeeId())) {
            return false;
        } else if (!addressClient.addressExists(shiftDTO.getStoreId())) {
            return false;
        } else if (shiftDTO.getShiftDate().isBefore(LocalDate.now())) {
            return false;
        } else if (shiftDTO.getStartTime().isBefore(LocalTime.parse(START_TIME))
                || shiftDTO.getEndTime().isAfter(LocalTime.parse(END_TIME))) {
            return false;
        }

        return true;
    }
}
