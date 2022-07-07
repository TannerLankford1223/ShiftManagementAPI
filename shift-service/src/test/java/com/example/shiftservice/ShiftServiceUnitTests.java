package com.example.shiftservice;

import com.example.shiftservice.domain.dto.DailySchedule;
import com.example.shiftservice.domain.dto.ScheduleRequest;
import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import com.example.shiftservice.domain.service.ShiftService;
import com.example.shiftservice.infrastructure.entity.Shift;
import com.example.shiftservice.infrastructure.exceptionhandler.InvalidRequestException;
import com.example.shiftservice.infrastructure.exceptionhandler.ShiftNotFoundException;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceUnitTests {

    @Mock
    private ShiftPersistencePort shiftRepo;

    private ShiftServicePort shiftService;

    private Shift shift;
    private ShiftRequest shiftRequest;
    private ShiftResponse shiftResponse;

    @BeforeEach
    public void init() {
        this.shiftService = new ShiftService(shiftRepo);
        this.shift = new Shift(405L, 16L, LocalDate.of(2022, 7, 6),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));
        this.shiftRequest = new ShiftRequest(16L, shift.getShiftDate(), shift.getStartTime(),
                shift.getEndTime());
        this.shiftResponse = new ShiftResponse(405L, "Jane", "Doe", "jane@acme.com",
                shift.getShiftDate(), shift.getStartTime(), shift.getEndTime() );
    }

    @Test
    public void createShift_ReturnsShiftResponse() {
        Mockito.when(shiftRepo.createShift(shift)).thenReturn(shift);

        ShiftResponse shiftResponse = shiftService.createShift(shiftRequest);

        assertEquals("jane@acme.com", shiftResponse.getEmployeeEmail());
        assertEquals(LocalDate.of(2022, 7,6), shiftResponse.getShiftDate());
        assertEquals(LocalTime.parse("8:00"), shiftResponse.getStartTime());
    }

    @Test
    public void createShift_DateInvalid_ReturnsInvalidRequestException() {
       shiftRequest.setShiftDate(LocalDate.of(2021, 5, 4));

       assertThrows(InvalidRequestException.class, () -> shiftService.createShift(shiftRequest));
    }

    @Test
    public void getEmployeeShift_ShiftExists_ReturnsShiftDTO() {
        Mockito.when(shiftRepo.getShift(shift.getId()))
                .thenReturn(Optional.of(shift));

        ShiftResponse shiftResponse = shiftService.getEmployeeShift(shift.getId());

        assertEquals(405L, shiftResponse.getId());
        assertEquals("jane@acme.com", shiftResponse.getEmployeeEmail());
        assertEquals("Jane", shiftResponse.getFirstName());
        assertEquals("Doe", shiftResponse.getLastName());
        assertEquals(LocalDate.of(2022, 7,6), shiftResponse.getShiftDate());
        assertEquals(LocalTime.parse("8:00"), shiftResponse.getStartTime());
    }

    @Test
    public void getEmployeeShift_ShiftNonExistent_ThrowsShiftNotFoundException() {
        Mockito.when(shiftRepo.getShift(7521L))
                .thenReturn(Optional.empty());

        assertThrows(ShiftNotFoundException.class, () -> shiftService.getEmployeeShift(7521L));
    }

    @Test
    public void getWorkSchedule_ReturnsWorkScheduleForRequestedTimePeriod() {
        Shift shift1 = new Shift(406L, 25L, LocalDate.of(2022, 7, 7),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));
        Shift shift2 = new Shift(407L, 135L, LocalDate.of(2022, 7, 8),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));

        ShiftResponse shiftResponse1 = new ShiftResponse(406L, "John", "Smith",
                "john@email.com", shift1.getShiftDate(), shift1.getStartTime(), shift1.getEndTime());
        ShiftResponse shiftResponse2 = new ShiftResponse(407L, "User", "Name",
                "user@yahoo.com", shift2.getShiftDate(), shift2.getStartTime(), shift2.getEndTime());

        DailySchedule day1 = new DailySchedule(LocalDate.of(2022, 7, 7),
                Set.of(shiftResponse, shiftResponse1));
        DailySchedule day2 = new DailySchedule(LocalDate.of(2022, 7, 8),
                Set.of(shiftResponse2));

        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setStartDate(day1.getDate());
        scheduleRequest.setEndDate(day2.getDate());

        Mockito.when(shiftRepo.getWorkSchedule(day1.getDate(), day2.getDate()))
                .thenReturn(List.of(shift, shift1, shift2));

        List<DailySchedule> schedule = shiftService.getWorkSchedule(scheduleRequest);

        assertEquals(LocalDate.of(2022, 7, 7), schedule.get(0).getDate());
        assertEquals(2, schedule.get(0).getDailyShifts().size());
        assertEquals(LocalDate.of(2022, 7, 8), schedule.get(1).getDate());
        assertEquals(1, schedule.get(1).getDailyShifts().size());
    }

    @Test
    public void getWorkSchedule_InvalidTimePeriod_throwsInvalidRequestException() {
        LocalDate startDate = LocalDate.of(2022, 7, 7);
        LocalDate endDate = LocalDate.of(2022, 7, 5);
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setStartDate(startDate);
        scheduleRequest.setEndDate(endDate);

        assertThrows(InvalidRequestException.class, () -> shiftService
                .getWorkSchedule(scheduleRequest));
    }

    @Test
    public void getEmployeeSchedule_ReturnsListOfShiftResponsesforEmployee() {
        Shift shift1 = new Shift(417L, 16L, LocalDate.of(2022, 7, 14),
                LocalTime.parse("11:00"), LocalTime.parse("17:00"));
        Shift shift2 = new Shift(435L, 16L, LocalDate.of(2022, 7, 17),
                LocalTime.parse("10:00"), LocalTime.parse("16:00"));

        Mockito.when(shiftRepo.getEmployeeSchedule(16L, shift1.getShiftDate(), shift2.getShiftDate()))
                .thenReturn(List.of(shift1, shift2));
    }
    @Test
    public void postWorkSchedule_ShiftRequestsValid_ExecutesSuccessfully() {
        Shift shift1 = new Shift(73L, LocalDate.of(2022, 7, 16),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));
        ShiftRequest shiftRequest1 = new ShiftRequest(shift1.getEmployeeId(), shift1.getShiftDate(),
                shift1.getStartTime(), shift1.getEndTime());

        Mockito.doNothing().when(shiftRepo).postWorkSchedule(List.of(shift, shift1));

        shiftService.postWorkSchedule(List.of(shiftRequest, shiftRequest1));

        verify(shiftRepo).postWorkSchedule(List.of(shift, shift1));
    }

    @Test
    public void postWorkSchedule_TimeNotValid_ThrowsInvalidShiftRequestException() {
        // Set to date in the past
        ShiftRequest shiftRequest1 = new ShiftRequest(73L, LocalDate.of(2022, 6, 1),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));

        assertThrows(InvalidRequestException.class, () -> shiftService
                .postWorkSchedule(List.of(shiftRequest, shiftRequest1)));
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

        assertThrows(ShiftNotFoundException.class, () -> shiftService.deleteEmployeeShift(7521L));
    }
}
