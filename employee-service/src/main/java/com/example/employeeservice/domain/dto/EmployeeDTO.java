package com.example.employeeservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @JsonProperty("employee_id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long id;

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
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    private String phoneNumber;

    public EmployeeDTO(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
