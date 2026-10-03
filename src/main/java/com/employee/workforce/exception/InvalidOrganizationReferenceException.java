package com.employee.workforce.exception;

public class InvalidOrganizationReferenceException extends RuntimeException {

    public InvalidOrganizationReferenceException(String message) {
        super(message);
    }
}