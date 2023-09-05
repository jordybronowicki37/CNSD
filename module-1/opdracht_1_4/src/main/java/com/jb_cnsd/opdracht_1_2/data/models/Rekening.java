package com.jb_cnsd.opdracht_1_2.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
public class Rekening {
    @Id
    private final String iban;
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
        this.iban = "";
        this.personen = new HashSet<>();
    }

    public Rekening(String iban, Persoon personen) {
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
