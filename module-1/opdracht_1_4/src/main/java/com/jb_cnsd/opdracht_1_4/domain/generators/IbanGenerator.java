package com.jb_cnsd.opdracht_1_4.domain.generators;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class IbanGenerator {
    private final Random random;

    public IbanGenerator() {
        this.random = new Random();
    }

    public IbanGenerator(Random random) {
        this.random = random;
    }

    public String generateNewIban() {
        var builder = new StringBuilder("NL99CNSD");
        for (int i = 0; i < 10; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
