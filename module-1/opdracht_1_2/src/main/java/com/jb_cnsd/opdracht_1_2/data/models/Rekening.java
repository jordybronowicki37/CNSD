package com.jb_cnsd.opdracht_1_2.data.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Rekening {
    private final String iban;
    private float saldo;
    private RekeningStatus status;
    private final List<RekeningHouder> rekeningHouders;

    public Rekening(String iban, RekeningHouder rekeningHouder) {
        this.iban = iban;
        saldo = 0;
        status = RekeningStatus.NORMAAL;
        rekeningHouders = new ArrayList<>(List.of(rekeningHouder));
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
