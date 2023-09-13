package com.jb_cnsd.opdracht_2_2.domain.service;

import com.jb_cnsd.opdracht_2_2.domain.external.DummyEmployeesResponse;
import com.jb_cnsd.opdracht_2_2.domain.external.DummyEmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DummyApiService {
    @Value("${urls.employeeAPI}")
    private String DUMMY_API_URL_BASE;
    private final RestTemplateBuilder restTemplateBuilder;

    public DummyEmployeesResponse getAllEmployees() {
        var restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(DUMMY_API_URL_BASE + "/api/v1/employees", DummyEmployeesResponse.class);
    }

    public DummyEmployeeResponse getEmployee(int id) {
        var restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(DUMMY_API_URL_BASE + "/api/v1/employee/" + id, DummyEmployeeResponse.class);
    }
}
