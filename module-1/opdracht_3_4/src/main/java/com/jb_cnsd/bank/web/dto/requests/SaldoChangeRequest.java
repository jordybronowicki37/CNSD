package com.jb_cnsd.bank.web.dto.requests;

import jakarta.validation.constraints.Positive;

public record SaldoChangeRequest(@Positive float saldo) {
}
