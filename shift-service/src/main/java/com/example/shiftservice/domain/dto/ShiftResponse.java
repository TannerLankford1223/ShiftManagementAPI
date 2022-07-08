package com.example.shiftservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ShiftResponse {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

    @JsonProperty("shift_id")
    private long id;

    @JsonProperty("employee_id")
    private long employeeId;

    @JsonProperty("shift_date")
    private LocalDate shiftDate;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    public ShiftResponse(long id, long employeeId, LocalDate shiftDate, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.employeeId = employeeId;
        this.shiftDate = shiftDate;
        this.startTime = formatter.format(startTime);
        this.endTime = formatter.format(endTime);
    }
}