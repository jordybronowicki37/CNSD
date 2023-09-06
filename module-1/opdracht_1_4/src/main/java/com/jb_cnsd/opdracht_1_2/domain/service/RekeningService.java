package com.jb_cnsd.opdracht_1_2.domain.service;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import com.jb_cnsd.opdracht_1_2.data.models.Persoon;
import com.jb_cnsd.opdracht_1_2.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_1_2.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.RekeningCreateDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.RekeningDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.RekeningEditDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonDto;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.AlreadyExistsException;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.NotFoundException;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.RekeningException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RekeningService {
    private final RekeningRepository rekeningRepository;
    private final PersoonRepository persoonRepository;

    public RekeningService(
            RekeningRepository rekeningRepository,
            PersoonRepository persoonRepository
    ) {
        this.rekeningRepository = rekeningRepository;
        this.persoonRepository = persoonRepository;
    }

    public List<Rekening> GetAll() {
        return rekeningRepository.findAll();
    }

    public Rekening Get(String iban) {
        return findRekening(iban);
    }

    public Rekening Create(RekeningCreateDto createDto) {
        var persoon = findPersoon(createDto.persoonBsn());
        var newRekening = new Rekening(createDto.iban(), persoon);

        if (rekeningRepository.existsById(createDto.persoonBsn())) throw new AlreadyExistsException("Er bestaat al een rekening met deze iban!");

        persoon.getRekeningen().add(newRekening);
        rekeningRepository.save(newRekening);
        return newRekening;
    }

    public Rekening Edit(String iban, RekeningEditDto editDto) {
        var rekening = findRekening(iban);
        rekening.setStatus(editDto.status());
        rekeningRepository.save(rekening);
        return rekening;
    }

    public void Remove(String iban) {
        var rekening = findRekening(iban);
        rekeningRepository.delete(rekening);
    }

    public Rekening AddSaldo(String iban, float saldo) {
        var rekening = findRekening(iban);
        rekening.setSaldo(rekening.getSaldo() + saldo);
        rekeningRepository.save(rekening);
        return rekening;
    }

    public Rekening RemoveSaldo(String iban, float saldo) {
        var rekening = findRekening(iban);
        rekening.setSaldo(rekening.getSaldo() - saldo);
        rekeningRepository.save(rekening);
        return rekening;
    }

    public List<Persoon> GetHouders(String iban) {
        var rekening = findRekening(iban);
        return rekening.getPersonen().stream().toList();
    }

    public Rekening AddHouder(String iban, String bsn) {
        var rekening = findRekening(iban);
        var persoon = findPersoon(bsn);

        rekening.getPersonen().add(persoon);
        persoon.getRekeningen().add(rekening);
        rekeningRepository.save(rekening);
        return rekening;
    }

    public Rekening RemoveHouder(String iban, String bsn) {
        var rekening = findRekening(iban);
        var persoon = findPersoon(bsn);

        // Controleer of dit de laatste rekeninghouder is
        var personen = rekening.getPersonen();
        if (personen.size() == 1 && personen.contains(persoon)) throw new RekeningException("De laatste rekeninghouder mag niet verwijderd worden!");

        rekening.getPersonen().remove(persoon);
        persoon.getRekeningen().remove(rekening);

        return rekening;
    }

    private Rekening findRekening(String iban) {
        var optionalRekening = rekeningRepository.findById(iban);
        if (optionalRekening.isEmpty()) throw new NotFoundException("Deze rekening is niet gevonden!");
        return optionalRekening.get();
    }

    private Persoon findPersoon(String bsn) {
        var optionalPersoon = persoonRepository.findById(bsn);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Deze persoon is niet gevonden!");
        return optionalPersoon.get();
    }
}
