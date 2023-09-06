package com.jb_cnsd.opdracht_1_2.web.controller;

import com.jb_cnsd.opdracht_1_2.domain.service.RekeningService;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekeningen gevonden"),
    })
    @GetMapping("")
    ResponseEntity<List<RekeningDto>> GetAll() {
        return new ResponseEntity<>(
                service.GetAll().stream().map(RekeningDto::new).toList(),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekening gevonden"),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @GetMapping("{iban}")
    ResponseEntity<RekeningDto> Get(@PathVariable String iban) {
        return new ResponseEntity<>(
                new RekeningDto(service.Get(iban)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rekening aangemaakt"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("")
    ResponseEntity<RekeningDto> Create(@RequestBody RekeningCreateDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.Create(body)),
                HttpStatus.CREATED
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekening is aangepast"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PutMapping("{iban}")
    ResponseEntity<RekeningDto> Create(@PathVariable String iban, @RequestBody RekeningEditDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.Edit(iban, body)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rekening is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{iban}")
    ResponseEntity<Void> Remove(@PathVariable String iban) {
        service.Remove(iban);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo is toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("{iban}/saldo")
    ResponseEntity<RekeningDto> AddHouder(@PathVariable String iban, @Valid @RequestBody SaldoDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.AddSaldo(iban, body.getSaldo())),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{iban}/saldo")
    ResponseEntity<RekeningDto> RemoveHouder(@PathVariable String iban, @Valid @RequestBody SaldoDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.RemoveSaldo(iban, body.getSaldo())),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon is toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("{iban}/persoon/{bsn}")
    ResponseEntity<RekeningDto> AddPersoon(@PathVariable String iban, @PathVariable String bsn) {
        return new ResponseEntity<>(
                new RekeningDto(service.AddPersoon(iban, bsn)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{iban}/persoon/{bsn}")
    ResponseEntity<RekeningDto> RemovePersoon(@PathVariable String iban, @PathVariable String bsn) {
        return new ResponseEntity<>(
                new RekeningDto(service.RemovePersoon(iban, bsn)),
                HttpStatus.OK
        );
    }
}
