package com.jb_cnsd.opdracht_2_1.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
public class Rekening extends BaseEntity {
    @Embedded
    private final Iban iban;

    private float saldo;

    private RekeningStatus status;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rekening rekening = (Rekening) o;
        return Objects.equals(iban, rekening.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }
}
