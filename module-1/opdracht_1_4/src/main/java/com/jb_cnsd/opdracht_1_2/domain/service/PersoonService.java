package com.jb_cnsd.opdracht_1_2.domain.service;

import com.jb_cnsd.opdracht_1_2.data.models.Persoon;
import com.jb_cnsd.opdracht_1_2.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonCreateDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonDto;
import com.jb_cnsd.opdracht_1_2.web.controller.dto.PersoonEditDto;
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

    public List<Persoon> GetAll() {
        return persoonRepository.findAll();
    }

    public Persoon Get(long id) {
        var optionalPersoon = persoonRepository.findById(id);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Deze persoon is niet gevonden!");
        return optionalPersoon.get();
    }

    public Persoon Create(PersoonCreateDto createDto) {
        var nieuwePersoon = new Persoon(createDto.bsn(), createDto.naam());
        if (persoonRepository.existsByBsn(createDto.bsn()))
            throw new AlreadyExistsException("Er bestaat al een persoon met deze bsn!");

        persoonRepository.save(nieuwePersoon);
        return nieuwePersoon;
    }

    public Persoon Edit(long id, PersoonEditDto editDto) {
        var persoon = Get(id);

        persoon.setNaam(editDto.naam());
        persoonRepository.save(persoon);
        return persoon;
    }

    public void Remove(long id) {
        persoonRepository.delete(Get(id));
    }
}
