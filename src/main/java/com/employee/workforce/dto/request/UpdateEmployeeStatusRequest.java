package com.employee.workforce.dto.request;

import com.employee.workforce.entity.EmployeeStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateEmployeeStatusRequest(

        @NotNull(message = "Employee status is required")
        EmployeeStatus status

) {
}