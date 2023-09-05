package com.jb_cnsd.opdracht_1_2.data.repository;

import com.jb_cnsd.opdracht_1_2.data.models.Persoon;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersoonRepository {
    private static final Set<Persoon> personen = new HashSet<>();

    public Set<Persoon> GetAll() {
        return personen;
    }

    public Persoon Get(String bsn) {
        var persoon = personen.stream().filter(r -> r.getBsn().equals(bsn)).findFirst();
        if (persoon.isEmpty()) throw new NotFoundException("De persoonBsn is niet gevonden!");
        return persoon.get();
    }

    public void Add(Persoon persoon) {
        personen.add(persoon);
    }

    public Persoon Remove(String bsn) {
        var persoon = Get(bsn);
        personen.remove(persoon);
        return persoon;
    }
}
