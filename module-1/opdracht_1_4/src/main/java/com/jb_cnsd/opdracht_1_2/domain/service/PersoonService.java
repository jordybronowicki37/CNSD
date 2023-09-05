package com.jb_cnsd.opdracht_1_2.domain.service;

import com.jb_cnsd.opdracht_1_2.data.models.Persoon;
import com.jb_cnsd.opdracht_1_2.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_1_2.domain.dto.PersoonCreateDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.PersoonDto;
import com.jb_cnsd.opdracht_1_2.domain.dto.PersoonEditDto;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.AlreadyExistsException;
import com.jb_cnsd.opdracht_1_2.domain.exceptions.NotFoundException;
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
        return persoonRepository.findAll().stream().map(PersoonDto::new).toList();
    }

    public PersoonDto Get(String iban) {
        var optionalPersoon = persoonRepository.findById(iban);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Deze persoon is niet gevonden!");
        return new PersoonDto(optionalPersoon.get());
    }

    public PersoonDto Create(PersoonCreateDto createDto) {
        var nieuwePersoon = new Persoon(createDto.bsn(), createDto.naam());
        if (persoonRepository.existsById(createDto.bsn()))
            throw new AlreadyExistsException("Er bestaat al een persoon met deze bsn!");

        persoonRepository.save(nieuwePersoon);
        return new PersoonDto(nieuwePersoon);
    }

    public PersoonDto Edit(String bsn, PersoonEditDto editDto) {
        var optionalPersoon = persoonRepository.findById(bsn);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Deze persoon is niet gevonden!");
        var persoon = optionalPersoon.get();

        persoon.setNaam(editDto.naam());
        persoonRepository.save(persoon);
        return new PersoonDto(persoon);
    }

    public void Remove(String bsn) {
        var optionalPersoon = persoonRepository.findById(bsn);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Deze persoon is niet gevonden!");
        persoonRepository.delete(optionalPersoon.get());
    }
}
