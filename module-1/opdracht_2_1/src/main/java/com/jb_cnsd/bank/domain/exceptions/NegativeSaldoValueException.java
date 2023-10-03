package com.jb_cnsd.bank.domain.exceptions;

public class NegativeSaldoValueException extends RuntimeException {
    public NegativeSaldoValueException() {
        super("Saldo value cannot be negative!");
    }
}
