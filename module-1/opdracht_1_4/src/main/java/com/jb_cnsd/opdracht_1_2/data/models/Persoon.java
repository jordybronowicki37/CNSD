package com.jb_cnsd.opdracht_1_2.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Persoon {
    @Id
    private final String bsn;
    
    private String naam;

    @ManyToMany(mappedBy = "personen")
    private final Set<Rekening> rekeningen;

    protected Persoon() {
        this.bsn = "";
        rekeningen = new HashSet<>();
    }

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
