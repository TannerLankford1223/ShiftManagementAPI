package com.example.shiftservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleRequest {

    @JsonProperty("employee_id")
    private long employeeId;

    @JsonProperty("store_id")
    @NotNull
    private String storeId;

    @JsonProperty("start_date")
    @NotNull
    private LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull
    private LocalDate endDate;

    public ScheduleRequest(String storeId, LocalDate startDate, LocalDate endDate) {
        this.storeId = storeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
