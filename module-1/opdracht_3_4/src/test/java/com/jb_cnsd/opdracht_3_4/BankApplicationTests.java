package com.jb_cnsd.opdracht_3_4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BankApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> {});
    }

}
