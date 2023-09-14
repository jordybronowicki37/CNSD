package com.jb_cnsd.opdracht_2_4.web.controller;

import com.jb_cnsd.opdracht_2_4.domain.service.RekeningService;
import com.jb_cnsd.opdracht_2_4.web.dto.requests.RekeningCreateRequest;
import com.jb_cnsd.opdracht_2_4.web.dto.requests.RekeningEditRequest;
import com.jb_cnsd.opdracht_2_4.web.dto.requests.SaldoChangeRequest;
import com.jb_cnsd.opdracht_2_4.web.dto.responses.RekeningResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rekening")
@AllArgsConstructor
public class RekeningController {
    private final RekeningService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekeningen gevonden"),
    })
    @GetMapping("")
    ResponseEntity<List<RekeningResponse>> getAll() {
        return new ResponseEntity<>(
                service.getAll().stream().map(RekeningResponse::new).toList(),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekening gevonden"),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @GetMapping("{rekeningId}")
    ResponseEntity<RekeningResponse> get(@PathVariable long rekeningId) {
        return new ResponseEntity<>(
                new RekeningResponse(service.get(rekeningId)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rekening aangemaakt"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("")
    ResponseEntity<RekeningResponse> create(@RequestBody RekeningCreateRequest body) {
        return new ResponseEntity<>(
                new RekeningResponse(service.create(body.persoonId())),
                HttpStatus.CREATED
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekening is aangepast"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PutMapping("{rekeningId}")
    ResponseEntity<RekeningResponse> edit(@PathVariable long rekeningId, @RequestBody RekeningEditRequest body) {
        return new ResponseEntity<>(
                new RekeningResponse(service.edit(rekeningId, body)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rekening is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{rekeningId}")
    ResponseEntity<Void> remove(@PathVariable long rekeningId) {
        service.remove(rekeningId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo is toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("{rekeningId}/saldo")
    ResponseEntity<RekeningResponse> addSaldo(@PathVariable long rekeningId, @Valid @RequestBody SaldoChangeRequest body) {
        return new ResponseEntity<>(
                new RekeningResponse(service.addSaldo(rekeningId, body.saldo())),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{rekeningId}/saldo")
    ResponseEntity<RekeningResponse> removeSaldo(@PathVariable long rekeningId, @Valid @RequestBody SaldoChangeRequest body) {
        return new ResponseEntity<>(
                new RekeningResponse(service.removeSaldo(rekeningId, body.saldo())),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon is toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("{rekeningId}/persoon/{persoonId}")
    ResponseEntity<RekeningResponse> addPersoon(@PathVariable long rekeningId, @PathVariable long persoonId) {
        return new ResponseEntity<>(
                new RekeningResponse(service.addPersoon(rekeningId, persoonId)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{rekeningId}/persoon/{persoonId}")
    ResponseEntity<RekeningResponse> removePersoon(@PathVariable long rekeningId, @PathVariable long persoonId) {
        return new ResponseEntity<>(
                new RekeningResponse(service.removePersoon(rekeningId, persoonId)),
                HttpStatus.OK
        );
    }
}
