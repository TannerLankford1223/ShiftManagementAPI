package com.example.shiftservice.infrastructure.persistence;

import com.example.shiftservice.infrastructure.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<Shift> findAllByDateBetween(Date startDate, Date endDate);
}
