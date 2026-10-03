package com.employee.workforce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient organizationRestClient(
            @Value("${services.organization.base-url}")
            String organizationBaseUrl
    ) {

        return RestClient.builder()
                .baseUrl(organizationBaseUrl)
                .build();
    }
}