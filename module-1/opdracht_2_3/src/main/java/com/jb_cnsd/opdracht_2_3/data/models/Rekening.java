package com.jb_cnsd.opdracht_2_3.data.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Rekening extends BaseEntity {
    @Embedded
    @EqualsAndHashCode.Include
    private final Iban iban;

    private float saldo;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private RekeningStatus status;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "_persoon_rekening",
            joinColumns = @JoinColumn(name = "rekening_id"),
            inverseJoinColumns = @JoinColumn(name = "persoon_id")
    )
    private final Set<Persoon> personen;

    protected Rekening() {
        this.iban = Iban.random();
        this.personen = new HashSet<>();
    }

    public Rekening(Iban iban, Persoon personen) {
        this.iban = iban;
        saldo = 0;
        status = RekeningStatus.NORMAAL;
        this.personen = new HashSet<>(Set.of(personen));
    }
}
