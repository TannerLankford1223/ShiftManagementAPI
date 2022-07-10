package com.example.shiftservice;

import com.example.shiftservice.domain.client.EmployeeClient;
import com.example.shiftservice.domain.dto.DailySchedule;
import com.example.shiftservice.domain.dto.ScheduleRequest;
import com.example.shiftservice.domain.dto.ShiftDTO;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import com.example.shiftservice.domain.service.ShiftService;
import com.example.shiftservice.infrastructure.entity.Shift;
import com.example.shiftservice.infrastructure.exceptionhandler.InvalidRequestException;
import com.example.shiftservice.infrastructure.exceptionhandler.ShiftNotFoundException;
import com.example.shiftservice.infrastructure.mapper.ShiftMapper;
import com.example.shiftservice.infrastructure.mapper.ShiftMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceUnitTests {

    @Mock
    private ShiftPersistencePort shiftRepo;

    ShiftMapper mapper;

    @Mock
    private EmployeeClient employeeClient;

    private ShiftServicePort shiftService;
    private Shift shift;
    private ShiftDTO shiftDTO;
    private ShiftDTO shiftResponse;

    @BeforeEach
    public void init() {
        this.mapper = new ShiftMapperImpl();
        this.shiftService = new ShiftService(shiftRepo, employeeClient, mapper);
        this.shiftDTO = new ShiftDTO(16L, "Store1001", LocalDate.now().plusDays(1),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));
        this.shift = mapper.shiftDTOToShift(shiftDTO);
        this.shift.setId(0L);
        this.shiftResponse = mapper.shiftToShiftDTO(shift);
    }

    @Test
    public void isValidShiftRequest_ShiftIsValid_ReturnsTrue() {
        Mockito.when(employeeClient.employeeExists(shiftDTO.getEmployeeId())).thenReturn(true);

        assertTrue(shiftService.isValidShiftRequest(shiftDTO));
    }

    @Test
    public void isValidShiftRequest_InvalidStartTime_ThrowsInvalidShiftTimeException() {
        Mockito.when(employeeClient.employeeExists(shiftDTO.getEmployeeId())).thenReturn(true);

        ShiftDTO badRequest = new ShiftDTO(16L, "Store1001", LocalDate.now().plusDays(14),
                LocalTime.parse("07:00"), LocalTime.parse("15:00"));

        assertFalse(shiftService.isValidShiftRequest(badRequest));
    }

    @Test
    public void isValidShiftRequest_InvalidEndTime_ThrowsInvalidShiftTimeException() {
        Mockito.when(employeeClient.employeeExists(shiftDTO.getEmployeeId())).thenReturn(true);

        ShiftDTO badRequest = new ShiftDTO(16L, "Store1001", LocalDate.now().plusDays(14),
                LocalTime.parse("08:00"), LocalTime.parse("20:00"));

        assertFalse(shiftService.isValidShiftRequest(badRequest));
    }

    @Test
    public void isValidShiftRequest_InvalidShiftDate_ThrowsInvalidShiftTimeException() {
        Mockito.when(employeeClient.employeeExists(shiftDTO.getEmployeeId())).thenReturn(true);

        ShiftDTO badRequest =
                new ShiftDTO(16L, "Store1001", LocalDate.now().minusDays(14),
                LocalTime.parse("08:00"), LocalTime.parse("17:00"));

        assertFalse(shiftService.isValidShiftRequest(badRequest));
    }

    @Test
    public void createShift_ReturnsShiftDTO() {
        Mockito.when(employeeClient.employeeExists(shiftDTO.getEmployeeId())).thenReturn(true);

        Mockito.when(shiftRepo.createShift(shift)).thenReturn(shift);

        ShiftDTO shiftResponse = shiftService.createShift(shiftDTO);

        assertEquals(16L, shiftResponse.getEmployeeId());
        assertEquals(LocalDate.now().plusDays(1), shiftResponse.getShiftDate());
        assertEquals(LocalTime.parse("08:00"), shiftResponse.getStartTime());
    }

    @Test
    public void createShift_DateInvalid_ReturnsInvalidRequestException() {
        Mockito.when(employeeClient.employeeExists(shiftDTO.getEmployeeId())).thenReturn(true);

       shiftDTO.setShiftDate(LocalDate.now().minusDays(3));

       assertThrows(InvalidRequestException.class, () -> shiftService.createShift(shiftDTO));
    }

    @Test
    public void getEmployeeShift_ShiftExists_ReturnsShiftDTO() {
        Mockito.when(shiftRepo.getShift(shift.getId()))
                .thenReturn(Optional.of(shift));

        ShiftDTO shiftResponse = shiftService.getEmployeeShift(shift.getId());

        assertEquals(0L, shiftResponse.getShiftId());
        assertEquals(16L, shiftResponse.getEmployeeId());
        assertEquals(LocalDate.now().plusDays(1), shiftResponse.getShiftDate());
        assertEquals(LocalTime.parse("08:00"), shiftResponse.getStartTime());
    }

    @Test
    public void getEmployeeShift_ShiftNonExistent_ThrowsShiftNotFoundException() {
        assertThrows(ShiftNotFoundException.class, () -> shiftService.getEmployeeShift(7521L));
    }

    @Test
    public void getWorkSchedule_ReturnsWorkScheduleForRequestedTimePeriod() {
        Shift shift1 = new Shift(406L, 25L, "Store1001", LocalDate.now().plusDays(1),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));
        Shift shift2 = new Shift(407L, 135L, "Store1001", LocalDate.now().plusDays(2),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));

        ShiftDTO shiftResponse1 = mapper.shiftToShiftDTO(shift1);
        ShiftDTO shiftResponse2 = mapper.shiftToShiftDTO(shift2);

        DailySchedule day1 = new DailySchedule(LocalDate.now().plusDays(1),
                List.of(shiftResponse, shiftResponse1));
        DailySchedule day2 = new DailySchedule(LocalDate.now().plusDays(2),
                List.of(shiftResponse2));

        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setStartDate(day1.getDate());
        scheduleRequest.setEndDate(day2.getDate());

        Mockito.when(shiftRepo.getWorkSchedule(day1.getDate(), day2.getDate()))
                .thenReturn(List.of(shift, shift1, shift2));

        List<DailySchedule> schedule = shiftService.getWorkSchedule(scheduleRequest);

        assertEquals(LocalDate.now().plusDays(1), schedule.get(0).getDate());
        assertEquals(2, schedule.get(0).getDailyShifts().size());
        assertEquals(LocalDate.now().plusDays(2), schedule.get(1).getDate());
        assertEquals(1, schedule.get(1).getDailyShifts().size());
    }

    @Test
    public void getWorkSchedule_InvalidTimePeriod_throwsInvalidRequestException() {
        LocalDate startDate = LocalDate.of(2022, 7, 7);
        LocalDate endDate = LocalDate.of(2022, 7, 5);
        ScheduleRequest scheduleRequest = new ScheduleRequest(startDate, endDate);

        assertThrows(InvalidRequestException.class, () -> shiftService
                .getWorkSchedule(scheduleRequest));
    }

    @Test
    public void getEmployeeSchedule_ReturnsListOfShiftsforEmployee() {
        Shift shift1 = new Shift(417L, 16L, "Store1001", LocalDate.now().plusDays(14),
                LocalTime.parse("11:00"), LocalTime.parse("17:00"));
        Shift shift2 = new Shift(435L, 16L, "Store1001", LocalDate.now().plusDays(7),
                LocalTime.parse("10:00"), LocalTime.parse("16:00"));
        ScheduleRequest scheduleRequest = new ScheduleRequest(16L, shift2.getShiftDate(),
                shift1.getShiftDate());

        Mockito.when(shiftRepo.getEmployeeSchedule(16L, shift2.getShiftDate(), shift1.getShiftDate()))
                .thenReturn(List.of(shift1, shift2));
        Mockito.when(employeeClient.employeeExists(shift1.getEmployeeId())).thenReturn(true);

        List<DailySchedule> employeeSchedule = shiftService.getEmployeeSchedule(scheduleRequest);

        assertEquals(LocalDate.now().plusDays(7), employeeSchedule.get(0).getDate());
        assertEquals(435L, employeeSchedule.get(0).getDailyShifts().get(0).getShiftId());
        assertEquals(LocalDate.now().plusDays(14), employeeSchedule.get(1).getDate());
        assertEquals(417L, employeeSchedule.get(1).getDailyShifts().get(0).getShiftId());
    }

    @Test
    public void getEmployeeSchedule_EmployeeNonExistent_ThrowsInvalidRequestException() {
        Mockito.when(employeeClient.employeeExists(15L)).thenReturn(false);

        ScheduleRequest request = new ScheduleRequest(15L, LocalDate.now(),
                LocalDate.now().plusDays(3));

        assertThrows(InvalidRequestException.class, () -> shiftService.getEmployeeSchedule(request));
    }
    @Test
    public void postWorkSchedule_ShiftRequestsValid_ExecutesSuccessfully() {
        Shift shift1 = new Shift(73L, "Store1001", LocalDate.of(2022, 7, 16),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));
        ShiftDTO shiftDTO1 = new ShiftDTO(shift1.getEmployeeId(), shift1.getStoreId(), shift1.getShiftDate(),
                shift1.getStartTime(), shift1.getEndTime());

        Mockito.when(employeeClient.employeeExists(shift.getEmployeeId())).thenReturn(true);
        Mockito.when(employeeClient.employeeExists(shift1.getEmployeeId())).thenReturn(true);
        Mockito.doNothing().when(shiftRepo).postWorkSchedule(List.of(shift, shift1));

        shiftService.postWorkSchedule(List.of(shiftDTO, shiftDTO1));

        verify(shiftRepo).postWorkSchedule(List.of(shift, shift1));
    }

    @Test
    public void postWorkSchedule_TimeNotValid_ThrowsInvalidShiftRequestException() {
        // Set to date in the past
        ShiftDTO shiftDTO1 =
                new ShiftDTO(73L, "Store1001", LocalDate.of(2022, 6, 1),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));


        Mockito.when(employeeClient.employeeExists(shiftDTO.getEmployeeId())).thenReturn(true);
        Mockito.when(employeeClient.employeeExists(shiftDTO1.getEmployeeId())).thenReturn(true);

        assertThrows(InvalidRequestException.class, () -> shiftService
                .postWorkSchedule(List.of(shiftDTO, shiftDTO1)));
    }

    @Test
    public void deleteEmployeeShift_ShiftExists_DeletesShiftSuccessfully() {
        Mockito.when(shiftRepo.deleteShift(shift.getId())).thenReturn(shift);

        shiftService.deleteEmployeeShift(shift.getId());

        verify(shiftRepo).deleteShift(shift.getId());
    }

    @Test
    public void deleteEmployeeShift_ShiftNonExistent_ThrowsShiftNotFoundException() {
        Mockito.when(shiftRepo.deleteShift(7521L)).thenReturn(null);

        assertFalse(shiftService.deleteEmployeeShift(7521L));
    }
}
