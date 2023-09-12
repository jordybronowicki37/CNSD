package com.jb_cnsd.opdracht_2_1.domain.service;

import com.jb_cnsd.opdracht_2_1.data.models.Iban;
import com.jb_cnsd.opdracht_2_1.data.models.Rekening;
import com.jb_cnsd.opdracht_2_1.data.models.Persoon;
import com.jb_cnsd.opdracht_2_1.data.models.RekeningStatus;
import com.jb_cnsd.opdracht_2_1.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_2_1.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_2_1.web.dto.requests.RekeningEditRequest;
import com.jb_cnsd.opdracht_2_1.domain.exceptions.NotFoundException;
import com.jb_cnsd.opdracht_2_1.domain.exceptions.RekeningException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RekeningService {
    private final RekeningRepository rekeningRepository;
    private final PersoonRepository persoonRepository;

    @Cacheable(value = "rekeningen")
    public List<Rekening> GetAll() {
        return rekeningRepository.findAll();
    }

    @Cacheable(value = "rekeningen", key = "#id")
    public Rekening Get(long id) {
        return findRekening(id);
    }

    public Rekening Create(long persoonId) {
        var persoon = findPersoon(persoonId);

        // Generate a unique iban
        var newIban = Iban.random();
        while (rekeningRepository.existsByIban(newIban)){
            newIban = Iban.random();
        }

        var newRekening = new Rekening(newIban, persoon);

        persoon.getRekeningen().add(newRekening);
        rekeningRepository.save(newRekening);
        return newRekening;
    }

    public Rekening Edit(long id, RekeningEditRequest editDto) {
        var rekening = findRekening(id);
        rekening.setStatus(editDto.status());
        rekeningRepository.save(rekening);
        return rekening;
    }

    public void Remove(long id) {
        var rekening = findRekening(id);
        rekeningRepository.delete(rekening);
    }

    public Rekening AddSaldo(long id, float saldo) {
        var rekening = findRekening(id);
        if (rekening.getStatus() == RekeningStatus.GEBLOKKEERD) throw new RekeningException("De rekening is geblokkeerd");
        rekening.setSaldo(rekening.getSaldo() + saldo);
        rekeningRepository.save(rekening);
        return rekening;
    }

    public Rekening RemoveSaldo(long id, float saldo) {
        var rekening = findRekening(id);
        if (rekening.getStatus() == RekeningStatus.GEBLOKKEERD) throw new RekeningException("De rekening is geblokkeerd");
        rekening.setSaldo(rekening.getSaldo() - saldo);
        rekeningRepository.save(rekening);
        return rekening;
    }

    public Rekening AddPersoon(long id, long persoonId) {
        var rekening = findRekening(id);
        var persoon = findPersoon(persoonId);

        rekening.getPersonen().add(persoon);
        persoon.getRekeningen().add(rekening);
        rekeningRepository.save(rekening);
        return rekening;
    }

    public Rekening RemovePersoon(long id, long persoonId) {
        var rekening = findRekening(id);
        var persoon = findPersoon(persoonId);

        // Controleer of dit de laatste rekeninghouder is
        var personen = rekening.getPersonen();
        if (personen.size() == 1 && personen.contains(persoon)) throw new RekeningException("De laatste rekeninghouder mag niet verwijderd worden!");

        rekening.getPersonen().remove(persoon);
        persoon.getRekeningen().remove(rekening);
        rekeningRepository.save(rekening);
        return rekening;
    }

    private Rekening findRekening(long id) {
        var optionalRekening = rekeningRepository.findById(id);
        if (optionalRekening.isEmpty()) throw new NotFoundException("Deze rekening is niet gevonden!");
        return optionalRekening.get();
    }

    private Persoon findPersoon(long id) {
        var optionalPersoon = persoonRepository.findById(id);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Deze persoon is niet gevonden!");
        return optionalPersoon.get();
    }
}
