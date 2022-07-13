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

    private long storeId;

    private LocalDate shiftDate;

    private LocalTime startTime;
    
    private LocalTime endTime;

    public Shift(long employeeId, long storeId, LocalDate shiftDate, LocalTime startTime, LocalTime endTime) {
        this.employeeId = employeeId;
        this.storeId = storeId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
