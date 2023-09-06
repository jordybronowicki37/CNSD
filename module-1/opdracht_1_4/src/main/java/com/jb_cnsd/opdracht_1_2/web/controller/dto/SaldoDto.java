package com.jb_cnsd.opdracht_1_2.web.controller.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SaldoDto {
    @Positive
    private float saldo;
}
