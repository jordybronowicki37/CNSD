package com.jb_cnsd.opdracht_1_2.domain.dto;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import com.jb_cnsd.opdracht_1_2.data.models.RekeningHouder;

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
