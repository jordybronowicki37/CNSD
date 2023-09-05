package com.jb_cnsd.opdracht_1_2.domain.service;

import com.jb_cnsd.opdracht_1_2.data.models.Persoon;
import com.jb_cnsd.opdracht_1_2.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_1_2.domain.dto.PersoonCreateDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.PersoonDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.PersoonEditDto;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersoonService {
    private final PersoonRepository persoonRepository;

    public PersoonService(
            PersoonRepository persoonRepository
    ) {
        this.persoonRepository = persoonRepository;
    }

    public List<PersoonDto> GetAll() {
        return persoonRepository.GetAll().stream().map(PersoonDto::new).toList();
    }

    public PersoonDto Get(String iban) {
        return new PersoonDto(persoonRepository.Get(iban));
    }

    public PersoonDto Create(PersoonCreateDto createDto) {
        var nieuwePersoon = new Persoon(createDto.bsn(), createDto.naam());
        if (persoonRepository.GetAll().contains(nieuwePersoon))
            throw new AlreadyExistsException("Er bestaat al een persoonBsn met deze bsn!");

        persoonRepository.Add(nieuwePersoon);
        return new PersoonDto(nieuwePersoon);
    }

    public PersoonDto Edit(String bsn, PersoonEditDto editDto) {
        var persoon = persoonRepository.Get(bsn);
        persoon.setNaam(editDto.naam());
        return new PersoonDto(persoon);
    }

    public void Remove(String bsn) {
        var persoon = persoonRepository.Remove(bsn);
        persoon.getRekeningen().forEach(r -> r.getPersonen().remove(persoon));
    }
}
