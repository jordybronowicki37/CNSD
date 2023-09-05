package com.jb_cnsd.opdracht_1_2.data.repository;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RekeningRepository {
    private static final List<Rekening> rekeningen = new ArrayList<>();

    public List<Rekening> GetAll() {
        return rekeningen;
    }

    public Optional<Rekening> Get(String iban) {
        return rekeningen.stream().filter(r -> r.getIban().equals(iban)).findFirst();
    }

    public void Add(Rekening rekening) {
        if (!rekeningen.contains(rekening)) {
            rekeningen.add(rekening);
        }
    }

    public void Remove(String iban) {
        var rekening = Get(iban);
        if (rekening.isEmpty()) return;
        rekeningen.remove(rekening.get());
    }
}
