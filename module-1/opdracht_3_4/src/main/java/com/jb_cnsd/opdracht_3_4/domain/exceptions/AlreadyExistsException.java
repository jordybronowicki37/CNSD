package com.jb_cnsd.opdracht_3_4.domain.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
