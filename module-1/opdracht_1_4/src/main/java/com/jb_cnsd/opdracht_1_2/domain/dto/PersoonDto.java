package com.jb_cnsd.opdracht_1_2.domain.dto;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import com.jb_cnsd.opdracht_1_2.data.models.Persoon;

import java.util.List;

public record PersoonDto(String bsn, String naam, List<String> rekeningen) {
    public PersoonDto(Persoon persoon) {
        this(
                persoon.getBsn(),
                persoon.getNaam(),
                persoon.getRekeningen().stream().map(Rekening::getIban).toList()
        );
    }
}
