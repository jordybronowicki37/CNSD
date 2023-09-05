package com.jb_cnsd.opdracht_1_2.data.repository;

import com.jb_cnsd.opdracht_1_2.data.models.RekeningHouder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RekeningHouderRepository {
    private static final List<RekeningHouder> rekeningHouders = new ArrayList<>();

    public List<RekeningHouder> GetAll() {
        return rekeningHouders;
    }

    public Optional<RekeningHouder> Get(String bsn) {
        return rekeningHouders.stream().filter(r -> r.getBsn().equals(bsn)).findFirst();
    }

    public void Add(RekeningHouder rekeningHouder) {
        if (!rekeningHouders.contains(rekeningHouder)) {
            rekeningHouders.add(rekeningHouder);
        }
    }

    public void Remove(String bsn) {
        var rekeningHouder = Get(bsn);
        if (rekeningHouder.isEmpty()) return;
        rekeningHouders.remove(rekeningHouder.get());
    }
}
