package com.jb_cnsd.opdracht_1_2.web.dto.requests;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SaldoChangeRequest {
    @Positive
    private float saldo;
}
