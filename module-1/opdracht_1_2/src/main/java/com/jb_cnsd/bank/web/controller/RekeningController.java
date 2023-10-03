package com.jb_cnsd.bank.web.controller;

import com.jb_cnsd.bank.domain.dto.*;
import com.jb_cnsd.bank.domain.service.RekeningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rekening")
public class RekeningController {
    private final RekeningService service;

    public RekeningController(RekeningService service) {
        this.service = service;
    }

    @GetMapping("")
    ResponseEntity<List<RekeningDto>> GetAll() {
        return new ResponseEntity<>(service.GetAll(), HttpStatus.OK);
    }

    @GetMapping("{iban}")
    ResponseEntity<RekeningDto> Get(@PathVariable String iban) {
        return new ResponseEntity<>(service.Get(iban), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<RekeningDto> Create(@RequestBody RekeningCreateDto body) {
        return new ResponseEntity<>(service.Create(body), HttpStatus.CREATED);
    }

    @PutMapping("{iban}")
    ResponseEntity<RekeningDto> Create(@PathVariable String iban, @RequestBody RekeningEditDto body) {
        return new ResponseEntity<>(service.Edit(iban, body), HttpStatus.OK);
    }

    @DeleteMapping("{iban}")
    ResponseEntity<RekeningDto> Remove(@PathVariable String iban) {
        service.Remove(iban);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{iban}/saldo")
    ResponseEntity<RekeningDto> AddHouder(@PathVariable String iban, @Valid @RequestBody SaldoDto body) {
        return new ResponseEntity<>(service.AddSaldo(iban, body.getSaldo()), HttpStatus.OK);
    }

    @DeleteMapping("{iban}/saldo")
    ResponseEntity<RekeningDto> RemoveHouder(@PathVariable String iban, @Valid @RequestBody SaldoDto body) {
        return new ResponseEntity<>(service.RemoveSaldo(iban, body.getSaldo()), HttpStatus.OK);
    }

    @GetMapping("{iban}/houder")
    ResponseEntity<List<RekeningHouderDto>> GetHouders(@PathVariable String iban) {
        return new ResponseEntity<>(service.GetHouders(iban), HttpStatus.OK);
    }

    @PostMapping("{iban}/houder/{bsn}")
    ResponseEntity<RekeningDto> AddHouder(@PathVariable String iban, @PathVariable String bsn) {
        return new ResponseEntity<>(service.AddHouder(iban, bsn), HttpStatus.OK);
    }

    @DeleteMapping("{iban}/houder/{bsn}")
    ResponseEntity<RekeningDto> RemoveHouder(@PathVariable String iban, @PathVariable String bsn) {
        return new ResponseEntity<>(service.RemoveHouder(iban, bsn), HttpStatus.OK);
    }
}
