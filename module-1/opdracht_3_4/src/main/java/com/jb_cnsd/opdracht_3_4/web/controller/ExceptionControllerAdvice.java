package com.jb_cnsd.opdracht_3_4.web.controller;

import com.jb_cnsd.opdracht_3_4.domain.exceptions.AlreadyExistsException;
import com.jb_cnsd.opdracht_3_4.domain.exceptions.NotFoundException;
import com.jb_cnsd.opdracht_3_4.domain.exceptions.RekeningException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> handleNotFoundExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RekeningException.class})
    public ResponseEntity<String> handleBadRequestExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleConflictExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}