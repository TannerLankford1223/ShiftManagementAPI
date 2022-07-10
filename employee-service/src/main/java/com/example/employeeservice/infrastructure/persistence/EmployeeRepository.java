package com.example.employeeservice.infrastructure.persistence;

import com.example.employeeservice.infrastructure.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee deleteById(long employeeId);
}
