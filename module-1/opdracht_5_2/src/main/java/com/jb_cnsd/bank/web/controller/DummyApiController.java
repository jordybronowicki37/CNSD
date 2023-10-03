package com.jb_cnsd.opdracht_5_1.web.controller;

import com.jb_cnsd.opdracht_5_1.domain.external.DummyEmployeeResponse;
import com.jb_cnsd.opdracht_5_1.domain.external.DummyEmployeesResponse;
import com.jb_cnsd.opdracht_5_1.domain.service.DummyApiService;
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
    public ResponseEntity<DummyEmployeesResponse> getAll() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @GetMapping("{id}")
    public ResponseEntity<DummyEmployeeResponse> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getEmployee(id));
    }
}
