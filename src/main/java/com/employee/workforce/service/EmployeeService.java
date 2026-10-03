package com.employee.workforce.service;

import com.employee.workforce.dto.request.CreateEmployeeRequest;
import com.employee.workforce.dto.request.UpdateEmployeeRequest;
import com.employee.workforce.dto.request.UpdateEmployeeStatusRequest;
import com.employee.workforce.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(CreateEmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse updateEmployee(
            Long id,
            UpdateEmployeeRequest request
    );

    EmployeeResponse updateEmployeeStatus(
            Long id,
            UpdateEmployeeStatusRequest request
    );
}