package com.jb_cnsd.bank.web.dto.responses;

import com.jb_cnsd.bank.data.models.Rekening;
import com.jb_cnsd.bank.data.models.Persoon;
import com.jb_cnsd.bank.data.models.RekeningStatus;

import java.util.List;

public record RekeningResponse(long id, String iban, float saldo, RekeningStatus status, List<Long> rekeningHouders) {
    public RekeningResponse(Rekening rekening) {
        this(
                rekening.getId(),
                rekening.getIban(),
                rekening.getSaldo(),
                rekening.getStatus(),
                rekening.getPersonen().stream().map(Persoon::getId).toList()
        );
    }
}
