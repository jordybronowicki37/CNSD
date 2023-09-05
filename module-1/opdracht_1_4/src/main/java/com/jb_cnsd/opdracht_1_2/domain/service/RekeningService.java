package com.jb_cnsd.opdracht_1_2.domain.service;

import com.jb_cnsd.opdracht_1_2.data.models.Rekening;
import com.jb_cnsd.opdracht_1_2.data.models.RekeningHouder;
import com.jb_cnsd.opdracht_1_2.data.repository.RekeningHouderRepository;
import com.jb_cnsd.opdracht_1_2.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningCreateDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningEditDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningHouderDto;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.AlreadyExistsException;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.RekeningException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RekeningService {
    private final RekeningRepository rekeningRepository;
    private final RekeningHouderRepository rekeningHouderRepository;

    public RekeningService(
            RekeningRepository rekeningRepository,
            RekeningHouderRepository rekeningHouderRepository
    ) {
        this.rekeningRepository = rekeningRepository;
        this.rekeningHouderRepository = rekeningHouderRepository;
    }

    public List<RekeningDto> GetAll() {
        return rekeningRepository.GetAll().stream().map(RekeningDto::new).toList();
    }

    public RekeningDto Get(String iban) {
        return new RekeningDto(rekeningRepository.Get(iban));
    }

    public RekeningDto Create(RekeningCreateDto createDto) {
        var rekeningHouder = findRekeningHouder(createDto.rekeningHouder());
        var newRekening = new Rekening(createDto.iban(), rekeningHouder);

        if (rekeningRepository.GetAll().contains(newRekening)) throw new AlreadyExistsException("Er bestaat al een rekening met deze iban!");

        rekeningHouder.getRekeningen().add(newRekening);
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
        rekening.getRekeningHouders().forEach(r -> r.getRekeningen().remove(rekening));
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

    public List<RekeningHouderDto> GetHouders(String iban) {
        var rekening = rekeningRepository.Get(iban);
        return rekening.getRekeningHouders().stream().map(RekeningHouderDto::new).toList();
    }

    public RekeningDto AddHouder(String iban, String bsn) {
        var rekening = rekeningRepository.Get(iban);
        var houder = findRekeningHouder(bsn);

        rekening.getRekeningHouders().add(houder);
        houder.getRekeningen().add(rekening);
        return new RekeningDto(rekening);
    }

    public RekeningDto RemoveHouder(String iban, String bsn) {
        var rekening = rekeningRepository.Get(iban);
        var houder = findRekeningHouder(bsn);

        // Controleer of dit de laatste rekeninghouder is
        var alleHouders = rekening.getRekeningHouders();
        if (alleHouders.size() == 1 && alleHouders.contains(houder)) throw new RekeningException("De laatste rekeninghouder mag niet verwijderd worden!");

        rekening.getRekeningHouders().remove(houder);
        houder.getRekeningen().remove(rekening);

        return new RekeningDto(rekening);
    }

    private RekeningHouder findRekeningHouder(String bsn) {
        return rekeningHouderRepository.Get(bsn);
    }
}
