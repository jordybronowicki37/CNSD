package com.jb_cnsd.opdracht_1_2.web.controller;

import com.jb_cnsd.opdracht_1_2.domain.exceptions.AlreadyExistsException;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.NotFoundException;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.RekeningException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler({NotFoundException.class, RekeningException.class})
    public ResponseEntity<String> handleCustomException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleCustomException(AlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}