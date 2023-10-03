package com.jb_cnsd.bank.domain.dto;

import com.jb_cnsd.bank.data.models.Rekening;
import com.jb_cnsd.bank.data.models.RekeningHouder;

import java.util.List;

public record RekeningHouderDto(String bsn, String naam, List<String> rekeningen) {
    public RekeningHouderDto(RekeningHouder rekeningHouder) {
        this(
                rekeningHouder.getBsn(),
                rekeningHouder.getNaam(),
                rekeningHouder.getRekeningen().stream().map(Rekening::getIban).toList()
        );
    }
}
