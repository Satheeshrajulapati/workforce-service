package com.employee.workforce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_employees_employee_code",
                        columnNames = "employee_code"
                ),
                @UniqueConstraint(
                        name = "uk_employees_email",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "employee_code",
            nullable = false,
            length = 30
    )
    private String employeeCode;

    @Column(
            name = "first_name",
            nullable = false,
            length = 100
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            length = 100
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            length = 150
    )
    private String email;

    @Column(
            name = "phone_number",
            length = 20
    )
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(
            name = "department_id",
            nullable = false
    )
    private Long departmentId;

    @Column(
            name = "designation_id",
            nullable = false
    )
    private Long designationId;

    @Column(
            name = "location_id",
            nullable = false
    )
    private Long locationId;

    @Column(
            name = "employment_type_id",
            nullable = false
    )
    private Long employmentTypeId;

    @Column(
            name = "joining_date",
            nullable = false
    )
    private LocalDate joiningDate;

    @Column(
            name = "salary",
            precision = 12,
            scale = 2
    )
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private EmployeeStatus status;

    @Column(
            name = "created_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private LocalDateTime updatedAt;
}