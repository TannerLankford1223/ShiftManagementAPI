package com.example.shiftservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftResponse {

    @JsonProperty("shift_id")
    private long id;

    @JsonProperty("employee_first_name")
    private String firstName;

    @JsonProperty("employee_last_name")
    private String lastName;

    @JsonProperty("employee_email")
    @Email(message = "Please enter a valid email address")
    private String employeeEmail;

    @JsonProperty("date")
    @Pattern(regexp = "[0-9]{2}/[0-9]{2}/[0-9]{4}")
    private LocalDate shiftDate;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;
}