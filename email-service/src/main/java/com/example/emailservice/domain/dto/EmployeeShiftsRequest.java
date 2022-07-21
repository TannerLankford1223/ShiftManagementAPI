package com.example.emailservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeShiftsRequest {

    @JsonProperty("employee_id")
    private long employeeId;

    @JsonProperty("store_id")
    private long storeId;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    public EmployeeShiftsRequest(long storeId, LocalDate startDate, LocalDate endDate) {
        this.storeId = storeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
