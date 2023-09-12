package com.jb_cnsd.opdracht_2_1.domain.exceptions;

public class NegativeSaldoValueException extends RuntimeException {
    public NegativeSaldoValueException() {
        super("Saldo value cannot be negative!");
    }
}
