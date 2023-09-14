package com.jb_cnsd.opdracht_2_4.web.dto.responses;

import com.jb_cnsd.opdracht_2_4.data.models.Rekening;
import com.jb_cnsd.opdracht_2_4.data.models.Persoon;

import java.util.List;

public record PersoonResponse(long id, String bsn, String naam, List<Long> rekeningen) {
    public PersoonResponse(Persoon persoon) {
        this(
                persoon.getId(),
                persoon.getBsn(),
                persoon.getNaam(),
                persoon.getRekeningen().stream().map(Rekening::getId).toList()
        );
    }
}
