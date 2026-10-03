package com.employee.workforce.client;

import com.employee.workforce.client.dto.OrganizationReferenceResponse;
import com.employee.workforce.exception.OrganizationReferenceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class OrganizationClient {

    private final RestClient organizationRestClient;

    public OrganizationClient(RestClient organizationRestClient) {
        this.organizationRestClient = organizationRestClient;
    }

    public OrganizationReferenceResponse getDepartment(Long id) {
        return getReference(
                "/api/organization/departments/{id}",
                id,
                "Department"
        );
    }

    public OrganizationReferenceResponse getDesignation(Long id) {
        return getReference(
                "/api/organization/designations/{id}",
                id,
                "Designation"
        );
    }

    public OrganizationReferenceResponse getLocation(Long id) {
        return getReference(
                "/api/organization/locations/{id}",
                id,
                "Location"
        );
    }

    public OrganizationReferenceResponse getEmploymentType(Long id) {
        return getReference(
                "/api/organization/employment-types/{id}",
                id,
                "Employment type"
        );
    }

    private OrganizationReferenceResponse getReference(
            String uri,
            Long id,
            String resourceName
    ) {
        try {
            return organizationRestClient
                    .get()
                    .uri(uri, id)
                    .retrieve()
                    .body(OrganizationReferenceResponse.class);

        } catch (RestClientResponseException exception) {

            if (exception.getStatusCode().value() == 404) {
                throw new OrganizationReferenceNotFoundException(
                        resourceName + " not found with id: " + id
                );
            }

            throw exception;
        }
    }
}