package com.example.emailservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeShifts {

    private long employeeId;

    private String firstName;

    private String lastName;

    private String employeeEmail;

    List<Shift> employeeShifts;
}
