package com.jb_cnsd.opdracht_1_2.web.controller;

import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonCreateDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonEditDto;
import com.jb_cnsd.opdracht_1_2.domain.service.PersoonService;
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

    @GetMapping("")
    ResponseEntity<List<PersoonDto>> GetAll() {
        return new ResponseEntity<>(
                service.GetAll().stream().map(PersoonDto::new).toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("{bsn}")
    ResponseEntity<PersoonDto> Get(@PathVariable String bsn) {
        return new ResponseEntity<>(
                new PersoonDto(service.Get(bsn)),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    ResponseEntity<PersoonDto> Create(@RequestBody PersoonCreateDto body) {
        return new ResponseEntity<>(
                new PersoonDto(service.Create(body)),
                HttpStatus.OK
        );
    }

    @PutMapping("{bsn}")
    ResponseEntity<PersoonDto> Edit(@PathVariable String bsn, @RequestBody PersoonEditDto body) {
        return new ResponseEntity<>(
                new PersoonDto(service.Edit(bsn, body)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("{bsn}")
    ResponseEntity<PersoonDto> Remove(@PathVariable String bsn) {
        service.Remove(bsn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
