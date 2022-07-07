package com.example.shiftservice.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "shifts")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long employeeId;

    @Temporal(TemporalType.DATE)
    private LocalDate shiftDate;

    @Temporal(TemporalType.TIME)
    private LocalTime startTime;

    @Temporal(TemporalType.TIME)
    private LocalTime endTime;

    public Shift(long employeeId, LocalDate shiftDate, LocalTime startTime, LocalTime endTime) {
        this.employeeId = employeeId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
