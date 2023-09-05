package com.jb_cnsd.opdracht_1_2.data.repository;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RekeningRepository {
    private static final Set<Rekening> rekeningen = new HashSet<>();

    public Set<Rekening> GetAll() {
        return rekeningen;
    }

    public Optional<Rekening> Get(String iban) {
        return rekeningen.stream().filter(r -> r.getIban().equals(iban)).findFirst();
    }

    public void Add(Rekening rekening) {
        rekeningen.add(rekening);
    }

    public Rekening Remove(String iban) {
        var rekening = Get(iban);
        if (rekening.isEmpty()) throw new NullPointerException();
        rekeningen.remove(rekening.get());
        return rekening.get();
    }
}
