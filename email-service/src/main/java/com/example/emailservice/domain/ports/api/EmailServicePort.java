package com.example.emailservice.domain.ports.api;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;

public interface EmailServicePort {

    String sendWeeklySchedule(long storeId, LocalDate startOfWeek) throws MessagingException, TemplateException,
            IOException;

    String updateEmployeeSchedule(long shiftId, String action) throws MessagingException, TemplateException, IOException;
}
