package com.jb_cnsd.opdracht_2_2.web.dto.requests;

import jakarta.validation.constraints.Positive;

public record SaldoChangeRequest(@Positive float saldo) {
}