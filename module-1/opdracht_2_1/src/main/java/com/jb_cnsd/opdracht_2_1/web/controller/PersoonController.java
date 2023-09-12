package com.jb_cnsd.opdracht_2_1.web.controller;

import com.jb_cnsd.opdracht_2_1.web.dto.requests.PersoonCreateRequest;
import com.jb_cnsd.opdracht_2_1.web.dto.responses.PersoonResponse;
import com.jb_cnsd.opdracht_2_1.web.dto.requests.PersoonEditRequest;
import com.jb_cnsd.opdracht_2_1.domain.service.PersoonService;
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
@RequestMapping("persoon")
@AllArgsConstructor
public class PersoonController {
    private final PersoonService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personen gevonden"),
    })
    @GetMapping("")
    ResponseEntity<List<PersoonResponse>> GetAll() {
        return new ResponseEntity<>(
                service.GetAll().stream().map(PersoonResponse::new).toList(),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon gevonden"),
            @ApiResponse(responseCode = "404", description = "Persoon is niet gevonden", content = @Content),
    })
    @GetMapping("{persoonId}")
    ResponseEntity<PersoonResponse> Get(@PathVariable long persoonId) {
        return new ResponseEntity<>(
                new PersoonResponse(service.Get(persoonId)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persoon toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "409", description = "Er bestaat al een persoon met deze credentials", content = @Content),
    })
    @PostMapping("")
    ResponseEntity<PersoonResponse> Create(@Valid @RequestBody PersoonCreateRequest body) {
        return new ResponseEntity<>(
                new PersoonResponse(service.Create(body)),
                HttpStatus.CREATED
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon aangepast"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
    })
    @PutMapping("{persoonId}")
    ResponseEntity<PersoonResponse> Edit(@PathVariable long persoonId, @RequestBody PersoonEditRequest body) {
        return new ResponseEntity<>(
                new PersoonResponse(service.Edit(persoonId, body)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persoon verwijderd"),
            @ApiResponse(responseCode = "404", description = "Persoon is niet gevonden", content = @Content),
    })
    @DeleteMapping("{persoonId}")
    ResponseEntity<Void> Remove(@PathVariable long persoonId) {
        service.Remove(persoonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
