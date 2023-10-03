package com.jb_cnsd.bank.domain.service;

import com.jb_cnsd.bank.data.models.Persoon;
import com.jb_cnsd.bank.data.repository.PersoonRepository;
import com.jb_cnsd.bank.web.dto.requests.PersoonCreateRequest;
import com.jb_cnsd.bank.web.dto.requests.PersoonEditRequest;
import com.jb_cnsd.bank.domain.exceptions.AlreadyExistsException;
import com.jb_cnsd.bank.domain.exceptions.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "personen")
    public List<Persoon> GetAll() {
        return persoonRepository.findAll();
    }

    @Cacheable(value = "personen", key = "#id")
    public Persoon Get(long id) {
        return findPersoon(id);
    }

    public Persoon Create(PersoonCreateRequest createDto) {
        var nieuwePersoon = new Persoon(createDto.bsn(), createDto.naam());
        if (persoonRepository.existsByBsn(createDto.bsn()))
            throw new AlreadyExistsException("Er bestaat al een persoon met deze bsn!");

        persoonRepository.save(nieuwePersoon);
        return nieuwePersoon;
    }

    public Persoon Edit(long id, PersoonEditRequest editDto) {
        var persoon = findPersoon(id);

        persoon.setNaam(editDto.naam());
        persoonRepository.save(persoon);
        return persoon;
    }

    public void Remove(long id) {
        persoonRepository.delete(findPersoon(id));
    }

    private Persoon findPersoon(long id) {
        var optionalPersoon = persoonRepository.findById(id);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Deze persoon is niet gevonden!");
        return optionalPersoon.get();
    }
}
