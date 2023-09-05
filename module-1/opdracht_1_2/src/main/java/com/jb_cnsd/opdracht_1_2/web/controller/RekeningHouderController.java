package com.jb_cnsd.opdracht_1_2.web.controller;

import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningHouderCreateDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningHouderDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningHouderEditDto;
import com.jb_cnsd.opdracht_1_2.domain.service.RekeningHouderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rekeninghouder")
public class RekeningHouderController {
    private final RekeningHouderService service;

    public RekeningHouderController(RekeningHouderService service) {
        this.service = service;
    }

    @GetMapping("")
    ResponseEntity<List<RekeningHouderDto>> GetAll() {
        return new ResponseEntity<>(service.GetAll(), HttpStatus.OK);
    }

    @GetMapping("{bsn}")
    ResponseEntity<RekeningHouderDto> Get(@PathVariable String bsn) {
        return new ResponseEntity<>(service.Get(bsn), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<RekeningHouderDto> Create(@RequestBody RekeningHouderCreateDto body) {
        return new ResponseEntity<>(service.Create(body), HttpStatus.OK);
    }

    @PutMapping("{bsn}")
    ResponseEntity<RekeningHouderDto> Edit(@PathVariable String bsn, @RequestBody RekeningHouderEditDto body) {
        return new ResponseEntity<>(service.Edit(bsn, body), HttpStatus.OK);
    }

    @DeleteMapping("{bsn}")
    ResponseEntity<RekeningHouderDto> Remove(@PathVariable String bsn) {
        service.Remove(bsn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
