package com.jb_cnsd.opdracht_1_2.domain.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class SaldoDto {
    @Min(0)
    private float saldo;
}
