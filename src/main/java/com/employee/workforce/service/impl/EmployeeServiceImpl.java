package com.employee.workforce.service.impl;

import com.employee.workforce.client.OrganizationClient;
import com.employee.workforce.client.dto.OrganizationReferenceResponse;
import com.employee.workforce.dto.request.CreateEmployeeRequest;
import com.employee.workforce.dto.request.UpdateEmployeeStatusRequest;
import com.employee.workforce.dto.response.EmployeeResponse;
import com.employee.workforce.entity.Employee;
import com.employee.workforce.entity.EmployeeStatus;
import com.employee.workforce.exception.DuplicateEmployeeException;
import com.employee.workforce.exception.EmployeeNotFoundException;
import com.employee.workforce.exception.InvalidOrganizationReferenceException;
import com.employee.workforce.repository.EmployeeRepository;
import com.employee.workforce.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import java.util.List;
import com.employee.workforce.dto.request.UpdateEmployeeRequest;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrganizationClient organizationClient;
    private final EntityManager entityManager;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            OrganizationClient organizationClient,
            EntityManager entityManager
    ) {
        this.employeeRepository = employeeRepository;
        this.organizationClient = organizationClient;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public EmployeeResponse createEmployee(
            CreateEmployeeRequest request
    ) {

        validateDuplicates(request);

        validateOrganizationReferences(
                request.departmentId(),
                request.designationId(),
                request.locationId(),
                request.employmentTypeId()
        );

        Employee employee = new Employee();

        employee.setEmployeeCode(
                request.employeeCode().trim().toUpperCase()
        );

        employee.setFirstName(request.firstName().trim());
        employee.setLastName(request.lastName().trim());

        employee.setEmail(
                request.email().trim().toLowerCase()
        );

        employee.setPhoneNumber(request.phoneNumber());
        employee.setDateOfBirth(request.dateOfBirth());

        employee.setDepartmentId(request.departmentId());
        employee.setDesignationId(request.designationId());
        employee.setLocationId(request.locationId());
        employee.setEmploymentTypeId(
                request.employmentTypeId()
        );

        employee.setJoiningDate(request.joiningDate());
        employee.setSalary(request.salary());

        employee.setStatus(EmployeeStatus.ACTIVE);

        Employee savedEmployee =
                employeeRepository.saveAndFlush(employee);

        entityManager.refresh(savedEmployee);

        return toResponse(savedEmployee);
    }

    private void validateDuplicates(
            CreateEmployeeRequest request
    ) {

        if (employeeRepository
                .existsByEmployeeCodeIgnoreCase(
                        request.employeeCode().trim()
                )) {

            throw new DuplicateEmployeeException(
                    "Employee code already exists"
            );
        }

        if (employeeRepository
                .existsByEmailIgnoreCase(
                        request.email().trim()
                )) {

            throw new DuplicateEmployeeException(
                    "Employee email already exists"
            );
        }
    }

    private void validateOrganizationReferences(
            Long departmentId,
            Long designationId,
            Long locationId,
            Long employmentTypeId
    ) {

        validateActive(
                organizationClient.getDepartment(departmentId),
                "Department"
        );

        validateActive(
                organizationClient.getDesignation(designationId),
                "Designation"
        );

        validateActive(
                organizationClient.getLocation(locationId),
                "Location"
        );

        validateActive(
                organizationClient.getEmploymentType(employmentTypeId),
                "Employment type"
        );
    }

    private void validateActive(
            OrganizationReferenceResponse response,
            String resourceName
    ) {

        if (response == null) {
            throw new InvalidOrganizationReferenceException(
                    resourceName + " response is empty"
            );
        }

        if (!Boolean.TRUE.equals(response.active())) {
            throw new InvalidOrganizationReferenceException(
                    resourceName + " is inactive"
            );
        }
    }

    private EmployeeResponse toResponse(Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeCode(),

                employee.getFirstName(),
                employee.getLastName(),

                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getDateOfBirth(),

                employee.getDepartmentId(),
                employee.getDesignationId(),
                employee.getLocationId(),
                employee.getEmploymentTypeId(),

                employee.getJoiningDate(),
                employee.getSalary(),

                employee.getStatus(),

                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(
            Long id,
            UpdateEmployeeRequest request
    ) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        String normalizedEmail =
                request.email().trim().toLowerCase();

        if (employeeRepository.existsByEmailIgnoreCaseAndIdNot(
                normalizedEmail,
                id
        )) {
            throw new DuplicateEmployeeException(
                    "Employee email already exists"
            );
        }

        validateOrganizationReferences(
                request.departmentId(),
                request.designationId(),
                request.locationId(),
                request.employmentTypeId()
        );

        employee.setFirstName(request.firstName().trim());
        employee.setLastName(request.lastName().trim());

        employee.setEmail(normalizedEmail);
        employee.setPhoneNumber(request.phoneNumber());
        employee.setDateOfBirth(request.dateOfBirth());

        employee.setDepartmentId(request.departmentId());
        employee.setDesignationId(request.designationId());
        employee.setLocationId(request.locationId());
        employee.setEmploymentTypeId(
                request.employmentTypeId()
        );

        employee.setJoiningDate(request.joiningDate());
        employee.setSalary(request.salary());

        Employee updatedEmployee =
                employeeRepository.saveAndFlush(employee);

        entityManager.refresh(updatedEmployee);

        return toResponse(updatedEmployee);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployeeStatus(
            Long id,
            UpdateEmployeeStatusRequest request
    ) {

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employee.setStatus(request.status());

        Employee updatedEmployee =
                employeeRepository.saveAndFlush(employee);

        entityManager.refresh(updatedEmployee);

        return toResponse(updatedEmployee);
    }
}