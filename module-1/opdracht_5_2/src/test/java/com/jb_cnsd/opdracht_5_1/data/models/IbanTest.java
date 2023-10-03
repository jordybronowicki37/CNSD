package com.jb_cnsd.opdracht_5_1.data.models;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class IbanTest {

    @Test
    void testRandomIban() {
        var iban = Iban.random();

        assertNotNull(iban);
        assertNotNull(iban.toString());
        assertNotEquals(0, iban.toString().length());
    }

    @Test
    void testRandomIbanWithRandomClass() {
        var iban = Iban.random(new Random(0));

        assertNotNull(iban);
        assertEquals("NL99CNSD0897531194", iban.toString());
    }
}