package com.jb_cnsd.bank.web.controller;

import com.jb_cnsd.bank.domain.service.LoginService;
import com.jb_cnsd.bank.web.dto.requests.LoginRequest;
import com.jb_cnsd.bank.web.dto.responses.PersoonResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService service;

    @PostMapping("")
    public ResponseEntity<PersoonResponse> login(@RequestBody LoginRequest body) {
        return new ResponseEntity<>(
                new PersoonResponse(service.login(body.username(), body.password())),
                HttpStatus.OK
        );
    }
}
