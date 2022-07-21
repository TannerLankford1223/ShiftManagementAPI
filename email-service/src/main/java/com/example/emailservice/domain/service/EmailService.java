package com.example.emailservice.domain.service;

import com.example.emailservice.domain.client.EmployeeClient;
import com.example.emailservice.domain.client.ShiftClient;
import com.example.emailservice.domain.dto.Employee;
import com.example.emailservice.domain.dto.EmployeeShifts;
import com.example.emailservice.domain.dto.EmployeeShiftsRequest;
import com.example.emailservice.domain.dto.Shift;
import com.example.emailservice.domain.ports.api.EmailServicePort;
import com.example.emailservice.infrastructure.exceptionhandler.InvalidRequestException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService implements EmailServicePort {

    private final JavaMailSender mailSender;

    private final Configuration configuration;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${company.name}")
    private String companyName;

    @Value("${company.contact-information}")
    private String companyContactInformation;

    private final EmployeeClient employeeClient;
    private final ShiftClient shiftClient;

    @Override
    public String sendWeeklySchedule(long storeId, LocalDate startOfWeek) throws MessagingException,
            TemplateException, IOException {

        if (startOfWeek.isBefore(LocalDate.now())) {
            throw new InvalidRequestException("Invalid date: the start of the week must be on or after "
                    + LocalDate.now());
        }

        List<EmployeeShifts> schedules = processStoreScheduleRequest(storeId, startOfWeek);

        for (EmployeeShifts schedule : schedules) {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

                helper.setFrom(sender);
                helper.setTo(schedule.getEmployeeEmail());
                helper.setSubject("Your Weekly Schedule");
                helper.setText(buildStoreScheduleMessage(schedule, startOfWeek), true);
            try {
                mailSender.send(mimeMessage);
                log.info("Email sent successfully");
            } catch (MailException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email: "
                        + e.getMessage());
            }
        }

        return "Messages sent successfully...";
    }

    @Override
    public String updateEmployeeSchedule(long shiftId, String action) throws MessagingException, TemplateException,
            IOException {

        Shift shift = shiftClient.getEmployeeShift(shiftId);

        if (shift == null) {
            throw new InvalidRequestException("Shift with id " + shiftId + " not found");
        }

        Employee employee = employeeClient.getEmployee(shift.getEmployeeId());


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(sender);
        helper.setTo(employee.getEmail());
        helper.setSubject("Schedule Update");
        helper.setText(buildEmployeeUpdateMessage(employee, shift, action), true);
        try {
            mailSender.send(mimeMessage);
            log.info("Email sent successfully");
        } catch (MailException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email: "
                    + e.getMessage());
        }

        return "Update email sent successfully";
    }

    private List<EmployeeShifts> processStoreScheduleRequest(long storeId, LocalDate startOfWeek) {

        // Get all shifts for one week from a store with the given ID from the shift-service
        EmployeeShiftsRequest employeeShiftsRequest = new EmployeeShiftsRequest(storeId, startOfWeek,
                startOfWeek.plusDays(6));
        List<Shift> shifts = shiftClient.getWorkSchedule(employeeShiftsRequest);
        // Organize shifts by employee ID
        Map<Long, List<Shift>> shiftsGroupedByEmployee = shifts.stream()
                .collect(Collectors.groupingBy(Shift::getEmployeeId));

        List<EmployeeShifts> schedules = new ArrayList<>();

        // For each employee ID use employeeClient to get contact information from employee-service
        for (Map.Entry<Long, List<Shift>> employee : shiftsGroupedByEmployee.entrySet()) {
            Employee employeeDetails = employeeClient.getEmployee(employee.getKey());

            // Sort the employees shifts by date
            List<Shift> sortedShifts = employee.getValue().stream()
                    .sorted(Comparator.comparing(Shift::getShiftDate)).toList();

            // Create an employee schedule DTO, which represents the weekly shift schedule for the given employee
            EmployeeShifts employeeShifts = new EmployeeShifts(employeeDetails.getId(),
                    employeeDetails.getFirstName(), employeeDetails.getLastName(), employeeDetails.getEmail(),
                    sortedShifts);

            schedules.add(employeeShifts);
        }

        return schedules;
    }

    private String buildStoreScheduleMessage(EmployeeShifts schedule, LocalDate startOfWeek) throws IOException,
            TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        model.put("employeeName", schedule.getFirstName());
        model.put("startOfWeek", startOfWeek);
        model.put("shifts", schedule.getEmployeeShifts());
        model.put("contactInformation", companyContactInformation);
        model.put("companyName", companyName);

        configuration.getTemplate("store-schedule-template.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    private String buildEmployeeUpdateMessage(Employee employee, Shift shift, String action) throws IOException,
            TemplateException {
        log.debug("command: " + action);
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        model.put("employeeName", employee.getFirstName());
        model.put("action", action);
        model.put("shift", shift);
        model.put("contactInformation", companyContactInformation);
        model.put("companyName", companyName);

        configuration.getTemplate("employee-update-template.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
