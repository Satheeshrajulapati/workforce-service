package com.employee.workforce.exception;

public class OrganizationReferenceNotFoundException extends RuntimeException {

    public OrganizationReferenceNotFoundException(String message) {
        super(message);
    }
}