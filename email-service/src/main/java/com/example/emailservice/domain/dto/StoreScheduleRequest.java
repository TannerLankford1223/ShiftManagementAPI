package com.example.emailservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreScheduleRequest {

    @JsonProperty("store_id")
    private long storeId;

    @JsonProperty("start_of_week_date")
    private LocalDate startOfWeekDate;
}
