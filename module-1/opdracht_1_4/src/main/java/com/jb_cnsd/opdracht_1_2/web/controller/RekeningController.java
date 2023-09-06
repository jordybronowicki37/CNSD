package com.jb_cnsd.opdracht_1_2.web.controller;

import com.jb_cnsd.opdracht_1_2.domain.service.RekeningService;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.*;
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
        return new ResponseEntity<>(
                service.GetAll().stream().map(RekeningDto::new).toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("{iban}")
    ResponseEntity<RekeningDto> Get(@PathVariable String iban) {
        return new ResponseEntity<>(
                new RekeningDto(service.Get(iban)),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    ResponseEntity<RekeningDto> Create(@RequestBody RekeningCreateDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.Create(body)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{iban}")
    ResponseEntity<RekeningDto> Create(@PathVariable String iban, @RequestBody RekeningEditDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.Edit(iban, body)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("{iban}")
    ResponseEntity<RekeningDto> Remove(@PathVariable String iban) {
        service.Remove(iban);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{iban}/saldo")
    ResponseEntity<RekeningDto> AddHouder(@PathVariable String iban, @Valid @RequestBody SaldoDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.AddSaldo(iban, body.getSaldo())),
                HttpStatus.OK
        );
    }

    @DeleteMapping("{iban}/saldo")
    ResponseEntity<RekeningDto> RemoveHouder(@PathVariable String iban, @Valid @RequestBody SaldoDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.RemoveSaldo(iban, body.getSaldo())),
                HttpStatus.OK
        );
    }

    @PostMapping("{iban}/persoon/{bsn}")
    ResponseEntity<RekeningDto> AddPersoon(@PathVariable String iban, @PathVariable String bsn) {
        return new ResponseEntity<>(
                new RekeningDto(service.AddPersoon(iban, bsn)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("{iban}/persoon/{bsn}")
    ResponseEntity<RekeningDto> RemovePersoon(@PathVariable String iban, @PathVariable String bsn) {
        return new ResponseEntity<>(
                new RekeningDto(service.RemovePersoon(iban, bsn)),
                HttpStatus.OK
        );
    }
}
