package com.employee.workforce.dto.response;

import com.employee.workforce.entity.EmployeeStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeResponse(

        Long id,
        String employeeCode,

        String firstName,
        String lastName,

        String email,
        String phoneNumber,
        LocalDate dateOfBirth,

        Long departmentId,
        Long designationId,
        Long locationId,
        Long employmentTypeId,

        LocalDate joiningDate,
        BigDecimal salary,

        EmployeeStatus status,

        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}