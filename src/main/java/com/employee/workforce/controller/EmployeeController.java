package com.employee.workforce.controller;

import com.employee.workforce.dto.request.CreateEmployeeRequest;
import com.employee.workforce.dto.request.UpdateEmployeeStatusRequest;
import com.employee.workforce.dto.response.EmployeeResponse;
import com.employee.workforce.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.employee.workforce.dto.request.UpdateEmployeeRequest;

@RestController
@RequestMapping("/api/workforce/employees")
@Tag(
        name = "Employees",
        description = "APIs for managing employees"
)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Create employee",
            description = "Creates a new employee after validating organization references"
    )
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest request
    ) {

        EmployeeResponse response =
                employeeService.createEmployee(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Returns an employee using its unique identifier"
    )
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    @Operation(
            summary = "Get all employees",
            description = "Returns all employees"
    )
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees()
        );
    }

    @Operation(
            summary = "Update employee",
            description = "Updates an existing employee"
    )
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeRequest request
    ) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, request)
        );
    }

    @Operation(
            summary = "Update employee status",
            description = "Updates the status of an existing employee"
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<EmployeeResponse> updateEmployeeStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeStatusRequest request
    ) {

        return ResponseEntity.ok(
                employeeService.updateEmployeeStatus(id, request)
        );
    }
}