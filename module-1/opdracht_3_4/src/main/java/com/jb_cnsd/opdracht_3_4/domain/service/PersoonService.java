package com.jb_cnsd.opdracht_3_4.domain.service;

import com.jb_cnsd.opdracht_3_4.data.models.Persoon;
import com.jb_cnsd.opdracht_3_4.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_3_4.web.dto.requests.PersoonCreateRequest;
import com.jb_cnsd.opdracht_3_4.web.dto.requests.PersoonEditRequest;
import com.jb_cnsd.opdracht_3_4.domain.exceptions.AlreadyExistsException;
import com.jb_cnsd.opdracht_3_4.domain.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersoonService {
    private final PersoonRepository persoonRepository;

    @Cacheable(value = "personen")
    public List<Persoon> getAll() {
        return persoonRepository.findAll();
    }

    @Cacheable(value = "personen", key = "#id")
    public Persoon get(long id) {
        return findPersoon(id);
    }

    public Persoon create(PersoonCreateRequest createDto) {
        var nieuwePersoon = new Persoon(createDto.bsn(), createDto.naam());
        if (persoonRepository.existsByBsn(createDto.bsn()))
            throw new AlreadyExistsException("Er bestaat al een persoon met deze bsn!");

        persoonRepository.save(nieuwePersoon);
        return nieuwePersoon;
    }

    public Persoon edit(long id, PersoonEditRequest editDto) {
        var persoon = findPersoon(id);

        persoon.setNaam(editDto.naam());
        persoonRepository.save(persoon);
        return persoon;
    }

    public void remove(long id) {
        persoonRepository.delete(findPersoon(id));
    }

    private Persoon findPersoon(long id) {
        var optionalPersoon = persoonRepository.findById(id);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Deze persoon is niet gevonden!");
        return optionalPersoon.get();
    }
}
