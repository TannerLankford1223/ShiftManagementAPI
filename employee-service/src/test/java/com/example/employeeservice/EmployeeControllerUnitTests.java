package com.example.employeeservice;

import com.example.employeeservice.application.controller.EmployeeController;
import com.example.employeeservice.domain.dto.EmployeeDTO;
import com.example.employeeservice.domain.ports.api.EmployeeServicePort;
import com.example.employeeservice.infrastructure.entity.Employee;
import com.example.employeeservice.infrastructure.mapper.EmployeeMapper;
import com.example.employeeservice.infrastructure.mapper.EmployeeMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerUnitTests {

    @MockBean
    private EmployeeServicePort employeeService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private EmployeeMapper employeeMapper;

    private EmployeeDTO request;
    private Employee employee;
    private EmployeeDTO response;

    @BeforeEach
    public void init() {
        this.employeeMapper = new EmployeeMapperImpl();
        this.request = new EmployeeDTO("Jane", "Doe", "jane@protonmail.com",
                "555-867-5309");
        this.employee = employeeMapper.employeeDTOToEmployee(request);
        this.employee.setId(4296L);
        this.response = employeeMapper.employeeToEmployeeDTO(employee);
    }

    @Test
    public void registerEmployee_ReturnsEmployeeDTO() throws Exception {
        Mockito.when(employeeService.registerEmployee(request)).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee_id").value(employee.getId()))
                .andExpect(jsonPath("$.first_name").value(employee.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(employee.getLastName()))
                .andExpect(jsonPath("$.employee_email").value(employee.getEmail()))
                .andExpect(jsonPath("$.employee_phone").value(employee.getPhoneNumber()));
    }

    @Test
    public void getEmployee_ReturnsEmployeeDTO() throws Exception {
        Mockito.when(employeeService.getEmployee(employee.getId())).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/{employeeId}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee_id").value(employee.getId()))
                .andExpect(jsonPath("$.first_name").value(employee.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(employee.getLastName()))
                .andExpect(jsonPath("$.employee_email").value(employee.getEmail()))
                .andExpect(jsonPath("$.employee_phone").value(employee.getPhoneNumber()));
    }

    @Test
    public void getEmployees_ReturnsListOfEmployeeDTOs() throws Exception {
        EmployeeDTO response1 = new EmployeeDTO(2596L, "John", "Doe", "john@gmail.com",
                "111-111-1111");
        EmployeeDTO response2 = new EmployeeDTO(1969L, "Robert", "Plant",
                "robert@zepellin.com", "101-101-1010");

        Mockito.when(employeeService.getEmployees()).thenReturn(List.of(response, response1, response2));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    public void updateEmployee_ReturnsUpdatedEmployeeDTO() throws Exception {
        EmployeeDTO update = new EmployeeDTO(4296L, "George", "Harrison", "george@beatles.com",
                "555-867-5309");

        Mockito.when(employeeService.updateEmployee(update)).thenReturn(update);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/employee/update/{employeeId}", update.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee_id").value(update.getId()))
                .andExpect(jsonPath("$.first_name").value(update.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(update.getLastName()))
                .andExpect(jsonPath("$.employee_email").value(update.getEmail()));
    }

    @Test
    public void deleteEmployee_ReturnsStatus200() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployee(employee.getId());

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/employee/{employeeId}", employee.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("Employee with id " + employee.getId() + " deleted", content);
    }

    @Test
    public void employeeExists_ReturnsTrue() throws Exception {
        Mockito.when(employeeService.employeeExists(employee.getId())).thenReturn(true);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/employee/exists/{employeeId}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void employeeNonexistent_ReturnsFalse() throws Exception {
        Mockito.when(employeeService.employeeExists(employee.getId())).thenReturn(false);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/employee/exists/{employeeId}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
}
