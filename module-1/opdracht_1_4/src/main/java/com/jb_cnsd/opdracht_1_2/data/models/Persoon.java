package com.jb_cnsd.opdracht_1_2.data.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class Persoon {
    private final String bsn;
    private String naam;
    private final Set<Rekening> rekeningen;

    public Persoon(String bsn, String naam) {
        this.bsn = bsn;
        this.naam = naam;
        rekeningen = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persoon that = (Persoon) o;
        return Objects.equals(bsn, that.bsn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bsn);
    }
}
