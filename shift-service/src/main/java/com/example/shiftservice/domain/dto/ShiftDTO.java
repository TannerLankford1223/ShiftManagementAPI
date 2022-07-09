package com.example.shiftservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotNull
    private long employeeId;

    @JsonProperty("shift_date")
    @Pattern(regexp = "[0-9]{2}/[0-9]{2}/[0-9]{4}")
    private LocalDate shiftDate;

    @JsonProperty("start_time")
    @NotNull
    private LocalTime startTime;

    @JsonProperty("end_time")
    @NotNull
    private LocalTime endTime;

    public ShiftDTO(long employeeId, LocalDate shiftDate, LocalTime startTime, LocalTime endTime) {
        this.employeeId = employeeId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
