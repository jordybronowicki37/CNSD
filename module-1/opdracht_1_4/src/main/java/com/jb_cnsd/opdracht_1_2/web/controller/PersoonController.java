package com.jb_cnsd.opdracht_1_2.web.controller;

import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonCreateDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonEditDto;
import com.jb_cnsd.opdracht_1_2.domain.service.PersoonService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("persoon")
public class PersoonController {
    private final PersoonService service;

    public PersoonController(PersoonService service) {
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personen gevonden"),
    })
    @GetMapping("")
    ResponseEntity<List<PersoonDto>> GetAll() {
        return new ResponseEntity<>(
                service.GetAll().stream().map(PersoonDto::new).toList(),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon gevonden"),
            @ApiResponse(responseCode = "404", description = "Persoon is niet gevonden", content = @Content),
    })
    @GetMapping("{bsn}")
    ResponseEntity<PersoonDto> Get(@PathVariable long bsn) {
        return new ResponseEntity<>(
                new PersoonDto(service.Get(bsn)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persoon toegevoegd"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
            @ApiResponse(responseCode = "409", description = "Er bestaat al een persoon met deze credentials", content = @Content),
    })
    @PostMapping("")
    ResponseEntity<PersoonDto> Create(@RequestBody PersoonCreateDto body) {
        return new ResponseEntity<>(
                new PersoonDto(service.Create(body)),
                HttpStatus.CREATED
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persoon aangepast"),
            @ApiResponse(responseCode = "400", description = "Invalide argumenten meegegeven", content = @Content),
    })
    @PutMapping("{bsn}")
    ResponseEntity<PersoonDto> Edit(@PathVariable long bsn, @RequestBody PersoonEditDto body) {
        return new ResponseEntity<>(
                new PersoonDto(service.Edit(bsn, body)),
                HttpStatus.OK
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persoon verwijderd"),
            @ApiResponse(responseCode = "404", description = "Persoon is niet gevonden", content = @Content),
    })
    @DeleteMapping("{bsn}")
    ResponseEntity<Void> Remove(@PathVariable long bsn) {
        service.Remove(bsn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
