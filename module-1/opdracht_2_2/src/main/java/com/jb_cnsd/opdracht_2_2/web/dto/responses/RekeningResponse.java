package com.jb_cnsd.opdracht_2_2.web.dto.responses;

import com.jb_cnsd.opdracht_2_2.data.models.Rekening;
import com.jb_cnsd.opdracht_2_2.data.models.Persoon;
import com.jb_cnsd.opdracht_2_2.data.models.RekeningStatus;

import java.util.List;

public record RekeningResponse(long id, String iban, float saldo, RekeningStatus status, List<Long> rekeningHouders) {
    public RekeningResponse(Rekening rekening) {
        this(
                rekening.getId(),
                rekening.getIban().toString(),
                rekening.getSaldo(),
                rekening.getStatus(),
                rekening.getPersonen().stream().map(Persoon::getId).toList()
        );
    }
}