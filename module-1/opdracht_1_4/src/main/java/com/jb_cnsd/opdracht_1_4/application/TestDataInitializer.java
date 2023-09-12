package com.jb_cnsd.opdracht_1_4.application;

import com.jb_cnsd.opdracht_1_4.data.models.Persoon;
import com.jb_cnsd.opdracht_1_4.data.models.Rekening;
import com.jb_cnsd.opdracht_1_4.data.models.RekeningStatus;
import com.jb_cnsd.opdracht_1_4.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_1_4.domain.generators.IbanGenerator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.util.List;

@Component
@Profile("test")
public class TestDataInitializer {
    private final RekeningRepository rekeningRepository;
    private final IbanGenerator ibanGenerator;

    public TestDataInitializer(
            RekeningRepository rekeningRepository,
            IbanGenerator ibanGenerator) {
        this.rekeningRepository = rekeningRepository;
        this.ibanGenerator = ibanGenerator;
        Initialize();
    }

    @Transient
    private void Initialize() {
        var p1 = new Persoon("123456789", "Chris");
        var p2 = new Persoon("012345678", "Sarah");

        var r1 = new Rekening(ibanGenerator.generateNewIban(), p1);
        var r2 = new Rekening(ibanGenerator.generateNewIban(), p1);
        r2.getPersonen().add(p2);
        r2.setSaldo(500);
        var r3 = new Rekening(ibanGenerator.generateNewIban(), p2);
        r3.setSaldo(300);
        r3.setStatus(RekeningStatus.GEBLOKKEERD);
        rekeningRepository.saveAll(List.of(r1, r2, r3));
    }
}
