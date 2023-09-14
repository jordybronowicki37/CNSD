package com.jb_cnsd.opdracht_2_3.web.controller;

import com.jb_cnsd.opdracht_2_3.domain.external.DummyEmployeeResponse;
import com.jb_cnsd.opdracht_2_3.domain.external.DummyEmployeesResponse;
import com.jb_cnsd.opdracht_2_3.domain.service.DummyApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy-api")
@AllArgsConstructor
public class DummyApiController {
    private final DummyApiService service;

    @GetMapping("")
    ResponseEntity<DummyEmployeesResponse> getAll() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @GetMapping("{id}")
    ResponseEntity<DummyEmployeeResponse> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getEmployee(id));
    }
}
