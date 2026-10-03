CREATE TABLE employees (
    id BIGINT NOT NULL AUTO_INCREMENT,

    employee_code VARCHAR(30) NOT NULL,

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,

    email VARCHAR(150) NOT NULL,
    phone_number VARCHAR(20),

    date_of_birth DATE,

    department_id BIGINT NOT NULL,
    designation_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    employment_type_id BIGINT NOT NULL,

    joining_date DATE NOT NULL,

    salary DECIMAL(12, 2),

    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_employees
        PRIMARY KEY (id),

    CONSTRAINT uk_employees_employee_code
        UNIQUE (employee_code),

    CONSTRAINT uk_employees_email
        UNIQUE (email)
);