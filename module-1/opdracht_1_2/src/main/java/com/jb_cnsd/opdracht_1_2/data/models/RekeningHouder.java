package com.jb_cnsd.opdracht_1_2.data.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RekeningHouder {
    private final String bsn;
    private String naam;
    private final List<Rekening> rekeningen;

    public RekeningHouder(String bsn, String naam) {
        this.bsn = bsn;
        this.naam = naam;
        rekeningen = new ArrayList<>();
    }
}
