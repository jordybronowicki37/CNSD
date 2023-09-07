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
    @GetMapping("{rekeningId}")
    ResponseEntity<RekeningDto> Get(@PathVariable long rekeningId) {
        return new ResponseEntity<>(
                new RekeningDto(service.Get(rekeningId)),
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
                new RekeningDto(service.Create(body.persoonId())),
                HttpStatus.CREATED
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekening is aangepast"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PutMapping("{rekeningId}")
    ResponseEntity<RekeningDto> Create(@PathVariable long rekeningId, @RequestBody RekeningEditDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.Edit(rekeningId, body)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rekening is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{rekeningId}")
    ResponseEntity<Void> Remove(@PathVariable long rekeningId) {
        service.Remove(rekeningId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo is toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("{rekeningId}/saldo")
    ResponseEntity<RekeningDto> AddHouder(@PathVariable long rekeningId, @Valid @RequestBody SaldoDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.AddSaldo(rekeningId, body.getSaldo())),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{rekeningId}/saldo")
    ResponseEntity<RekeningDto> RemoveHouder(@PathVariable long rekeningId, @Valid @RequestBody SaldoDto body) {
        return new ResponseEntity<>(
                new RekeningDto(service.RemoveSaldo(rekeningId, body.getSaldo())),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon is toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("{rekeningId}/persoon/{persoonId}")
    ResponseEntity<RekeningDto> AddPersoon(@PathVariable long rekeningId, @PathVariable long persoonId) {
        return new ResponseEntity<>(
                new RekeningDto(service.AddPersoon(rekeningId, persoonId)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{rekeningId}/persoon/{persoonId}")
    ResponseEntity<RekeningDto> RemovePersoon(@PathVariable long rekeningId, @PathVariable long persoonId) {
        return new ResponseEntity<>(
                new RekeningDto(service.RemovePersoon(rekeningId, persoonId)),
                HttpStatus.OK
        );
    }
}
