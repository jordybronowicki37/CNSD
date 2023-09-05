package com.jb_cnsd.opdracht_1_2.domain.service;

import com.jb_cnsd.opdracht_1_2.data.models.RekeningHouder;
import com.jb_cnsd.opdracht_1_2.data.repository.RekeningHouderRepository;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningHouderCreateDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningHouderDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.RekeningHouderEditDto;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RekeningHouderService {
    private final RekeningHouderRepository rekeningHouderRepository;

    public RekeningHouderService(
            RekeningHouderRepository rekeningHouderRepository
    ) {
        this.rekeningHouderRepository = rekeningHouderRepository;
    }

    public List<RekeningHouderDto> GetAll() {
        return rekeningHouderRepository.GetAll().stream().map(RekeningHouderDto::new).toList();
    }

    public RekeningHouderDto Get(String iban) {
        return new RekeningHouderDto(rekeningHouderRepository.Get(iban));
    }

    public RekeningHouderDto Create(RekeningHouderCreateDto createDto) {
        var newRekeningHouder = new RekeningHouder(createDto.bsn(), createDto.naam());
        if (rekeningHouderRepository.GetAll().contains(newRekeningHouder))
            throw new AlreadyExistsException("Er bestaat al een rekeninghouder met deze bsn!");

        rekeningHouderRepository.Add(newRekeningHouder);
        return new RekeningHouderDto(newRekeningHouder);
    }

    public RekeningHouderDto Edit(String bsn, RekeningHouderEditDto editDto) {
        var rekeningHouder = rekeningHouderRepository.Get(bsn);
        rekeningHouder.setNaam(editDto.naam());
        return new RekeningHouderDto(rekeningHouder);
    }

    public void Remove(String bsn) {
        var rekeningHouder = rekeningHouderRepository.Remove(bsn);
        rekeningHouder.getRekeningen().forEach(r -> r.getRekeningHouders().remove(rekeningHouder));
    }
}
