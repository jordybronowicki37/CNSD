package com.jb_cnsd.opdracht_3_4.data.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Persoon extends BaseEntity {
    @Column(unique = true, length = 9, columnDefinition = "CHAR(9)")
    @EqualsAndHashCode.Include
    private final String bsn;

    @Column(length = 64)
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
}
