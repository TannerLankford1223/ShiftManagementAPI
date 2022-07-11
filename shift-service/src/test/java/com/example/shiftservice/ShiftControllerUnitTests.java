package com.example.shiftservice;

import com.example.shiftservice.application.controller.ShiftController;
import com.example.shiftservice.domain.dto.DailySchedule;
import com.example.shiftservice.domain.dto.ScheduleRequest;
import com.example.shiftservice.domain.dto.ShiftDTO;
import com.example.shiftservice.domain.ports.api.ShiftServicePort;
import com.example.shiftservice.infrastructure.entity.Shift;
import com.example.shiftservice.infrastructure.mapper.ShiftMapper;
import com.example.shiftservice.infrastructure.mapper.ShiftMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShiftController.class)
public class ShiftControllerUnitTests {

    @MockBean
    private ShiftServicePort shiftService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private ShiftMapper shiftMapper;

    private ShiftDTO request;
    private Shift shift;
    private ShiftDTO response;
    private final String storeId = "Store1001";

    @BeforeEach
    public void init() {
        this.shiftMapper = new ShiftMapperImpl();
        this.request = new ShiftDTO(25L, storeId, LocalDate.now().plusDays(2),
                LocalTime.parse("09:30"), LocalTime.parse("12:45"));
        this.shift = shiftMapper.shiftDTOToShift(request);
        this.shift.setId(405L);
        this.response = shiftMapper.shiftToShiftDTO(this.shift);
    }

    @Test
    public void createShift_ReturnsShiftResponse() throws Exception {
        Mockito.when(shiftService.createShift(request)).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/shift/new-shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shift_id").value(shift.getId()))
                .andExpect(jsonPath("$.employee_id").value(shift.getEmployeeId()))
                .andExpect(jsonPath("$.shift_date").value(shift.getShiftDate().toString()))
                .andExpect(jsonPath("$.start_time").value("09:30:00"))
                .andExpect(jsonPath("$.end_time").value("12:45:00"));
    }

    @Test
    public void getEmployeeShift_ReturnsShiftResponse() throws Exception {
        Mockito.when(shiftService.getEmployeeShift(shift.getId())).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/shift/{shiftId}", shift.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shift_id").value(shift.getId()))
                .andExpect(jsonPath("$.employee_id").value(shift.getEmployeeId()))
                .andExpect(jsonPath("$.shift_date").value(shift.getShiftDate().toString()))
                .andExpect(jsonPath("$.start_time").value("09:30:00"))
                .andExpect(jsonPath("$.end_time").value("12:45:00"));
    }

    @Test
    public void getWorkSchedule_ReturnsListOfDailySchedules() throws Exception {
        ShiftDTO response1 =
                new ShiftDTO(406L, 27L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("08:30"), LocalTime.parse("14:00"));
        ShiftDTO response2 =
                new ShiftDTO(407L, 35L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("08:30"), LocalTime.parse("14:00"));
        ShiftDTO response3 =
                new ShiftDTO(408L, 10L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("14:00"), LocalTime.parse("17:00"));

        ScheduleRequest scheduleRequest =
                new ScheduleRequest(storeId,response.getShiftDate(), response1.getShiftDate().plusDays(1));

        DailySchedule scheduleDay1 = new DailySchedule(response.getShiftDate(), List.of(response));
        DailySchedule scheduleDay2 = new DailySchedule(response1.getShiftDate(),
                List.of(response1, response2, response3));

        Mockito.when(shiftService.getWorkSchedule(scheduleRequest)).thenReturn(List.of(scheduleDay1, scheduleDay2));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/shift/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scheduleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andDo(print());
    }

    @Test
    public void getEmployeeSchedule_ReturnsAScheduleOfEmployeesShifts() throws Exception {
        ShiftDTO response1 =
                new ShiftDTO(406L, 27L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("08:30"), LocalTime.parse("14:00"));
        ShiftDTO response2 =
                new ShiftDTO(407L, 35L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("08:30"), LocalTime.parse("14:00"));
        ShiftDTO response3 =
                new ShiftDTO(408L, 10L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("14:00"), LocalTime.parse("17:00"));

        ScheduleRequest scheduleRequest =
                new ScheduleRequest(10L, storeId, response.getShiftDate(),
                        response1.getShiftDate().plusDays(1));

        DailySchedule scheduleDay = new DailySchedule(response1.getShiftDate(),
                List.of(response3));

        Mockito.when(shiftService.getWorkSchedule(scheduleRequest)).thenReturn(List.of(scheduleDay));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/shift/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scheduleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andDo(print());
    }

    @Test
    public void postWorkSchedule_ReturnsResponseEntity() throws Exception {
        ShiftDTO request1 = new ShiftDTO(27L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("08:30"), LocalTime.parse("14:00"));
        ShiftDTO request2 = new ShiftDTO(35L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("08:30"),  LocalTime.parse("14:00"));
        ShiftDTO request3 = new ShiftDTO(10L, storeId, LocalDate.now().plusDays(3),
                LocalTime.parse("14:00"), LocalTime.parse("17:00"));

        List<ShiftDTO> schedule = List.of(request1, request2, request3);

        Mockito.doNothing().when(shiftService).postWorkSchedule(schedule);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/shift/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(schedule)))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("Work schedule successfully posted", content);
    }

    @Test
    public void deleteEmployeeShift_ReturnsResponseEntity() throws Exception {
        Mockito.doNothing().when(shiftService).deleteEmployeeShift(shift.getId());

        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/shift/{shiftId}", shift.getId()))
                        .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("Shift successfully deleted", content);
    }
}
