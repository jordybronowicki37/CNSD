package com.jb_cnsd.opdracht_1_2.domain.service;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import com.jb_cnsd.opdracht_1_2.data.models.Persoon;
import com.jb_cnsd.opdracht_1_2.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_1_2.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningCreateDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningEditDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.PersoonDto;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.AlreadyExistsException;
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

    public List<RekeningDto> GetAll() {
        return rekeningRepository.GetAll().stream().map(RekeningDto::new).toList();
    }

    public RekeningDto Get(String iban) {
        return new RekeningDto(rekeningRepository.Get(iban));
    }

    public RekeningDto Create(RekeningCreateDto createDto) {
        var persoon = findPersoon(createDto.persoonBsn());
        var newRekening = new Rekening(createDto.iban(), persoon);

        if (rekeningRepository.GetAll().contains(newRekening)) throw new AlreadyExistsException("Er bestaat al een rekening met deze iban!");

        persoon.getRekeningen().add(newRekening);
        rekeningRepository.Add(newRekening);
        return new RekeningDto(newRekening);
    }

    public RekeningDto Edit(String iban, RekeningEditDto editDto) {
        var rekening = rekeningRepository.Get(iban);
        rekening.setStatus(editDto.status());
        return new RekeningDto(rekening);
    }

    public void Remove(String iban) {
        var rekening = rekeningRepository.Remove(iban);
        rekening.getPersonen().forEach(r -> r.getRekeningen().remove(rekening));
    }

    public RekeningDto AddSaldo(String iban, float saldo) {
        var rekening = rekeningRepository.Get(iban);
        rekening.setSaldo(rekening.getSaldo() + saldo);
        return new RekeningDto(rekening);
    }

    public RekeningDto RemoveSaldo(String iban, float saldo) {
        var rekening = rekeningRepository.Get(iban);
        rekening.setSaldo(rekening.getSaldo() - saldo);
        return new RekeningDto(rekening);
    }

    public List<PersoonDto> GetHouders(String iban) {
        var rekening = rekeningRepository.Get(iban);
        return rekening.getPersonen().stream().map(PersoonDto::new).toList();
    }

    public RekeningDto AddHouder(String iban, String bsn) {
        var rekening = rekeningRepository.Get(iban);
        var persoon = findPersoon(bsn);

        rekening.getPersonen().add(persoon);
        persoon.getRekeningen().add(rekening);
        return new RekeningDto(rekening);
    }

    public RekeningDto RemoveHouder(String iban, String bsn) {
        var rekening = rekeningRepository.Get(iban);
        var persoon = findPersoon(bsn);

        // Controleer of dit de laatste rekeninghouder is
        var personen = rekening.getPersonen();
        if (personen.size() == 1 && personen.contains(persoon)) throw new RekeningException("De laatste rekeninghouder mag niet verwijderd worden!");

        rekening.getPersonen().remove(persoon);
        persoon.getRekeningen().remove(rekening);

        return new RekeningDto(rekening);
    }

    private Persoon findPersoon(String bsn) {
        return persoonRepository.Get(bsn);
    }
}
