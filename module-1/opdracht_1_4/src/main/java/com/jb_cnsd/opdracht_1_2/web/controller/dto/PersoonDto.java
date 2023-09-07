package com.jb_cnsd.opdracht_1_2.web.controller.dto;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import com.jb_cnsd.opdracht_1_2.data.models.Persoon;

import java.util.List;

public record PersoonDto(long id, String bsn, String naam, List<Long> rekeningen) {
    public PersoonDto(Persoon persoon) {
        this(
                persoon.getId(),
                persoon.getBsn(),
                persoon.getNaam(),
                persoon.getRekeningen().stream().map(Rekening::getId).toList()
        );
    }
}
