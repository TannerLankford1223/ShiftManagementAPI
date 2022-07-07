package com.example.shiftservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySchedule {

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("daily_shifts")
    private Set<ShiftResponse> dailyShifts;
}
