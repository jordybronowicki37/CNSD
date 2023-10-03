package com.jb_cnsd.bank.data.repository;

import com.jb_cnsd.bank.data.models.RekeningHouder;
import com.jb_cnsd.bank.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RekeningHouderRepository {
    private static final Set<RekeningHouder> rekeningHouders = new HashSet<>();

    public Set<RekeningHouder> GetAll() {
        return rekeningHouders;
    }

    public RekeningHouder Get(String bsn) {
        var houder = rekeningHouders.stream().filter(r -> r.getBsn().equals(bsn)).findFirst();
        if (houder.isEmpty()) throw new NotFoundException("De rekeninghouder is niet gevonden!");
        return houder.get();
    }

    public void Add(RekeningHouder rekeningHouder) {
        rekeningHouders.add(rekeningHouder);
    }

    public RekeningHouder Remove(String bsn) {
        var rekeningHouder = Get(bsn);
        rekeningHouders.remove(rekeningHouder);
        return rekeningHouder;
    }
}
