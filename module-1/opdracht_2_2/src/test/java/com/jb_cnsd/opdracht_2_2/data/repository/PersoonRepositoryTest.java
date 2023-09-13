package com.jb_cnsd.opdracht_2_2.data.repository;

import com.jb_cnsd.opdracht_2_2.data.models.Persoon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersoonRepositoryTest {
    @Autowired
    private PersoonRepository repository;

    @BeforeEach
    void setUp() {
        var p1 = new Persoon("123456789", "Bob");
        repository.save(p1);
    }

    @ParameterizedTest
    @CsvSource({"123456789,true", "012345678,false"})
    void existsByBsn(String iban, boolean existsInDatabase) {
        // Act
        var result = repository.existsByBsn(iban);

        // Assert
        assertEquals(existsInDatabase, result);
    }
}