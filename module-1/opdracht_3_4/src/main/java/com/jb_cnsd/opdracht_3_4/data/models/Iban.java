package com.jb_cnsd.opdracht_3_4.data.models;

import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor
@EqualsAndHashCode
public class Iban {
    @Column(unique = true, length = 18, columnDefinition = "CHAR(18)")
    private String value;

    public Iban(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Iban random() {
        return random(new Random());
    }

    public static Iban random(Random random) {
        var builder = new StringBuilder("NL99CNSD");
        for (var i = 0; i < 10; i++) {
            builder.append(random.nextInt(10));
        }
        return new Iban(builder.toString());
    }
}
