package com.jb_cnsd.opdracht_1_2.data.repository;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RekeningRepository {
    private static final Set<Rekening> rekeningen = new HashSet<>();

    public Set<Rekening> GetAll() {
        return rekeningen;
    }

    public Rekening Get(String iban) {
        var rekening = rekeningen.stream().filter(r -> r.getIban().equals(iban)).findFirst();
        if (rekening.isEmpty()) throw new NotFoundException("De rekening is niet gevonden!");
        return rekening.get();
    }

    public void Add(Rekening rekening) {
        rekeningen.add(rekening);
    }

    public Rekening Remove(String iban) {
        var rekening = Get(iban);
        rekeningen.remove(rekening);
        return rekening;
    }
}
