package com.example.employeeservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @JsonProperty("first_name")
    @NotNull
    private String firstName;

    @JsonProperty("last_name")
    @NotNull
    private String lastName;

    @JsonProperty("employee_email")
    @NotNull
    @Email(message = "Email must be valid")
    private String email;

    @JsonProperty("employee_phone")
    @NotNull
    @Size(min = 10, max = 10, message = "Phone number must be valid")
    private String phoneNumber;
}
