package com.example.shiftservice;

import com.example.shiftservice.domain.dto.ShiftRequest;
import com.example.shiftservice.domain.dto.ShiftResponse;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import com.example.shiftservice.domain.ports.spi.ShiftPersistencePort;
import com.example.shiftservice.domain.service.ShiftService;
import com.example.shiftservice.infrastructure.entity.Shift;
import com.example.shiftservice.infrastructure.exceptionhandler.ShiftNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

    @BeforeEach
    public void init() {
        this.shiftService = new ShiftService(shiftRepo);
        this.shift = new Shift(405L, 16L, LocalDate.of(2022, 7, 6),
                LocalTime.parse("08:00"), LocalTime.parse("15:30"));
        this.shiftRequest = new ShiftRequest(16L, shift.getShiftDate(), shift.getStartTime(),
                shift.getEndTime());
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
