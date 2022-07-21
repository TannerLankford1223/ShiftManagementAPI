package com.example.emailservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Shift {

    @JsonProperty("shift_id")
    private long shiftId;

    @JsonProperty("employee_id")
    private long employeeId;

    @JsonProperty("store_id")
    private long storeId;

    @JsonProperty("shift_date")
    private LocalDate shiftDate;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;
}
