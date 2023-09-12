package com.jb_cnsd.opdracht_2_2.application;

import com.jb_cnsd.opdracht_2_2.data.models.Iban;
import com.jb_cnsd.opdracht_2_2.data.models.Persoon;
import com.jb_cnsd.opdracht_2_2.data.models.Rekening;
import com.jb_cnsd.opdracht_2_2.data.models.RekeningStatus;
import com.jb_cnsd.opdracht_2_2.data.repository.RekeningRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.util.List;

@Component
@Profile("test")
public class TestDataInitializer {
    private final RekeningRepository rekeningRepository;

    public TestDataInitializer(
            RekeningRepository rekeningRepository) {
        this.rekeningRepository = rekeningRepository;
        initialize();
    }

    @Transient
    private void initialize() {
        var p1 = new Persoon("123456789", "Chris");
        var p2 = new Persoon("012345678", "Sarah");

        var r1 = new Rekening(Iban.random(), p1);
        var r2 = new Rekening(Iban.random(), p1);
        r2.getPersonen().add(p2);
        r2.setSaldo(500);
        var r3 = new Rekening(Iban.random(), p2);
        r3.setSaldo(300);
        r3.setStatus(RekeningStatus.GEBLOKKEERD);
        rekeningRepository.saveAll(List.of(r1, r2, r3));
    }
}
