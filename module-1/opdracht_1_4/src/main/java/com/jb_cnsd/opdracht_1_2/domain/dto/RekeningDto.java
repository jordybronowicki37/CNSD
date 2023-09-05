package com.jb_cnsd.opdracht_1_2.domain.dto;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import com.jb_cnsd.opdracht_1_2.data.models.Persoon;
import com.jb_cnsd.opdracht_1_2.data.models.RekeningStatus;

import java.util.List;

public record RekeningDto(String iban, float saldo, RekeningStatus status, List<String> rekeningHouders) {
    public RekeningDto(Rekening rekening) {
        this(
                rekening.getIban(),
                rekening.getSaldo(),
                rekening.getStatus(),
                rekening.getPersonen().stream().map(Persoon::getBsn).toList()
        );
    }
}
