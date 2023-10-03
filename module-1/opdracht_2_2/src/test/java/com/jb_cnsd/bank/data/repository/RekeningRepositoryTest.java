package com.jb_cnsd.bank.data.repository;

import com.jb_cnsd.bank.data.models.Iban;
import com.jb_cnsd.bank.data.models.Persoon;
import com.jb_cnsd.bank.data.models.Rekening;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RekeningRepositoryTest {
    @Autowired
    private RekeningRepository repository;

    @BeforeEach
    void setUp() {
        var p1 = new Persoon("123456789", "Bob");
        var r1 = new Rekening(new Iban("NL99CNSD1234567890"), p1);
        repository.save(r1);
    }

    @ParameterizedTest
    @CsvSource({"NL99CNSD1234567890,true", "NL99CNSD0123456789,false"})
    void existsByBsn(String iban, boolean existsInDatabase) {
        // Act
        var result = repository.existsByIban(new Iban(iban));

        // Assert
        assertEquals(existsInDatabase, result);
    }
}