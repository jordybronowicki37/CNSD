package com.jb_cnsd.opdracht_2_1.data.models;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor
public class Iban {
    @Column(unique = true, length = 18, columnDefinition = "CHAR(18)")
    private String iban;

    public Iban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return iban;
    }

    public static Iban random() {
        return random(new Random());
    }

    public static Iban random(Random random) {
        var builder = new StringBuilder("NL99CNSD");
        for (int i = 0; i < 10; i++) {
            builder.append(random.nextInt(10));
        }
        return new Iban(builder.toString());
    }
}
