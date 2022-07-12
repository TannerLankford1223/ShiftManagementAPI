package com.example.shiftservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShiftDTO {

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

    public ShiftDTO(long employeeId, long storeId, LocalDate shiftDate, LocalTime startTime, LocalTime endTime) {
        this.employeeId = employeeId;
        this.storeId = storeId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
