package com.example.employeeservice;

import com.example.employeeservice.domain.dto.EmployeeDTO;
import com.example.employeeservice.domain.ports.api.EmployeeServicePort;
import com.example.employeeservice.domain.ports.spi.EmployeePersistencePort;
import com.example.employeeservice.domain.service.EmployeeService;
import com.example.employeeservice.infrastructure.entity.Employee;
import com.example.employeeservice.infrastructure.exceptionhandler.InvalidRequestException;
import com.example.employeeservice.infrastructure.mapper.EmployeeMapper;
import com.example.employeeservice.infrastructure.mapper.EmployeeMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTests {

    @Mock
    private EmployeePersistencePort employeeRepo;

    private EmployeeServicePort employeeService;

    private EmployeeMapper mapper;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void init() {
        this.mapper = new EmployeeMapperImpl();
        this.employeeService = new EmployeeService(employeeRepo, mapper);
        this.employee = new Employee(73L, "John", "Doe", "john@acme.com",
                "111-111-1111");
        this.employeeDTO = mapper.employeeToEmployeeDTO(this.employee);
    }


    @Test
    public void saveEmployee_ReturnsEmployeeDTO() {
        Mockito.when(employeeRepo.saveEmployee(employee)).thenReturn(employee);

        EmployeeDTO response = employeeService.registerEmployee(employeeDTO);

        verify(employeeRepo).saveEmployee(employee);
        assertEquals(73L, response.getId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("111-111-1111", response.getPhoneNumber());
    }

    @Test
    public void getEmployee_EmployeeExists_ReturnsEmployeeDTO() {
        Mockito.when(employeeRepo.getEmployee(employee.getId())).thenReturn(Optional.of(employee));

        EmployeeDTO response = employeeService.getEmployee(employee.getId());

        assertEquals(73L, response.getId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("111-111-1111", response.getPhoneNumber());
    }

    @Test
    public void getEmployee_EmployeeNonExistent_ThrowsInvalidRequestException() {
        Mockito.when(employeeRepo.getEmployee(employee.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidRequestException.class, () -> employeeService.getEmployee(employee.getId()));
    }

    @Test
    public void getEmployees_ReturnsListOfEmployeeDTOs() {
        Employee employee1 = new Employee(31L, "Jane", "Doe", "jane@acme.com",
                "222-222-2222");
        Employee employee2 = new Employee(112L, "Allison", "Smith", "allison@gmail.com",
                "333-333-3333");

        Mockito.when(employeeRepo.getEmployees()).thenReturn(List.of(employee, employee1, employee2));

        List<EmployeeDTO> employees = employeeService.getEmployees();

        verify(employeeRepo).getEmployees();
        assertEquals(3, employees.size());
    }

    @Test
    public void updateEmployee_EmployeeExists_ReturnsUpdatedEmployeeDTO() {
        EmployeeDTO update = employeeDTO;
        update.setFirstName("George");
        update.setLastName("Harrison");
        update.setEmail("george@beatles.com");
        update.setPhoneNumber("555-555-5555");

        Mockito.when(employeeRepo.getEmployee(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepo.saveEmployee(mapper.employeeDTOToEmployee(update))).thenReturn(employee);

        EmployeeDTO response = employeeService.updateEmployee(update);

        assertEquals(73L, response.getId());
        assertEquals("George", response.getFirstName());
        assertEquals("Harrison", response.getLastName());
        assertEquals("555-555-5555", response.getPhoneNumber());
    }

    @Test
    public void updateEmployee_EmployeeNonExistent_ThrowsInvalidRequestException() {
        EmployeeDTO update = new EmployeeDTO(705L, "User", "Name", "user@email.com",
                "505-555-1234");

        Mockito.when(employeeRepo.getEmployee(update.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidRequestException.class, () -> employeeService.updateEmployee(update));
    }

    @Test
    public void deleteEmployee_EmployeeExists_ReturnsStatus200() {
        Mockito.when(employeeRepo.deleteEmployee(employee.getId())).thenReturn(employee);

        employeeService.deleteEmployee(employee.getId());

        verify(employeeRepo).deleteEmployee(employee.getId());
    }

    @Test
    public void deleteEmployee_EmployeeNonExistent_ThrowsInvalidRequestException() {
        assertThrows(InvalidRequestException.class, () -> employeeService.deleteEmployee(111L));
    }

    @Test
    public void employeeExists_ReturnsTrue() {
        Mockito.when(employeeRepo.employeeExists(17L)).thenReturn(true);

        assertTrue(employeeService.employeeExists(17L));
    }

    @Test
    public void employeeNonExistent_ReturnsFalse() {
        Mockito.when(employeeRepo.employeeExists(123L)).thenReturn(false);

        assertFalse(employeeService.employeeExists(123L));
    }
}
