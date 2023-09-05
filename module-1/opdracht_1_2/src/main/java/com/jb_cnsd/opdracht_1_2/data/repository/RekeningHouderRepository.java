package com.jb_cnsd.opdracht_1_2.data.repository;

import com.jb_cnsd.opdracht_1_2.data.models.RekeningHouder;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RekeningHouderRepository {
    private static final Set<RekeningHouder> rekeningHouders = new HashSet<>();

    public Set<RekeningHouder> GetAll() {
        return rekeningHouders;
    }

    public Optional<RekeningHouder> Get(String bsn) {
        return rekeningHouders.stream().filter(r -> r.getBsn().equals(bsn)).findFirst();
    }

    public void Add(RekeningHouder rekeningHouder) {
        rekeningHouders.add(rekeningHouder);
    }

    public RekeningHouder Remove(String bsn) {
        var rekeningHouder = Get(bsn);
        if (rekeningHouder.isEmpty()) throw new NotFoundException("De rekening is niet gevonden");
        rekeningHouders.remove(rekeningHouder.get());
        return rekeningHouder.get();
    }
}
