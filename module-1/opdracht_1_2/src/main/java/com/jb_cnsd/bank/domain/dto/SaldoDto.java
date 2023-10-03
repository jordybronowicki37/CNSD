package com.jb_cnsd.bank.domain.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SaldoDto {
    @Positive
    private float saldo;
}
