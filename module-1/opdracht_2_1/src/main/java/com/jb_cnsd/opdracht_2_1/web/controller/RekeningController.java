package com.jb_cnsd.opdracht_2_1.web.controller;

import com.jb_cnsd.opdracht_2_1.domain.service.RekeningService;
import com.jb_cnsd.opdracht_2_1.web.dto.requests.RekeningCreateRequest;
import com.jb_cnsd.opdracht_2_1.web.dto.requests.RekeningEditRequest;
import com.jb_cnsd.opdracht_2_1.web.dto.requests.SaldoChangeRequest;
import com.jb_cnsd.opdracht_2_1.web.dto.responses.RekeningResponse;
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
    ResponseEntity<List<RekeningResponse>> GetAll() {
        return new ResponseEntity<>(
                service.GetAll().stream().map(RekeningResponse::new).toList(),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekening gevonden"),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @GetMapping("{rekeningId}")
    ResponseEntity<RekeningResponse> Get(@PathVariable long rekeningId) {
        return new ResponseEntity<>(
                new RekeningResponse(service.Get(rekeningId)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rekening aangemaakt"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("")
    ResponseEntity<RekeningResponse> Create(@RequestBody RekeningCreateRequest body) {
        return new ResponseEntity<>(
                new RekeningResponse(service.Create(body.persoonId())),
                HttpStatus.CREATED
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rekening is aangepast"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PutMapping("{rekeningId}")
    ResponseEntity<RekeningResponse> Create(@PathVariable long rekeningId, @RequestBody RekeningEditRequest body) {
        return new ResponseEntity<>(
                new RekeningResponse(service.Edit(rekeningId, body)),
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
    ResponseEntity<RekeningResponse> AddHouder(@PathVariable long rekeningId, @Valid @RequestBody SaldoChangeRequest body) {
        return new ResponseEntity<>(
                new RekeningResponse(service.AddSaldo(rekeningId, body.saldo())),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{rekeningId}/saldo")
    ResponseEntity<RekeningResponse> RemoveHouder(@PathVariable long rekeningId, @Valid @RequestBody SaldoChangeRequest body) {
        return new ResponseEntity<>(
                new RekeningResponse(service.RemoveSaldo(rekeningId, body.saldo())),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon is toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @PostMapping("{rekeningId}/persoon/{persoonId}")
    ResponseEntity<RekeningResponse> AddPersoon(@PathVariable long rekeningId, @PathVariable long persoonId) {
        return new ResponseEntity<>(
                new RekeningResponse(service.AddPersoon(rekeningId, persoonId)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon is verwijderd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rekening is niet gevonden", content = @Content),
    })
    @DeleteMapping("{rekeningId}/persoon/{persoonId}")
    ResponseEntity<RekeningResponse> RemovePersoon(@PathVariable long rekeningId, @PathVariable long persoonId) {
        return new ResponseEntity<>(
                new RekeningResponse(service.RemovePersoon(rekeningId, persoonId)),
                HttpStatus.OK
        );
    }
}
