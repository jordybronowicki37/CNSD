package com.jb_cnsd.bank.domain.service;

import com.jb_cnsd.bank.data.models.Persoon;
import com.jb_cnsd.bank.data.repository.PersoonRepository;
import com.jb_cnsd.bank.domain.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private final PersoonRepository repository;

    @Cacheable(value = "credentials", key = "{#name, #password}")
    public Persoon login(String name, String password) {
        var optionalPersoon = repository.findByNaam(name);
        if (optionalPersoon.isEmpty()) throw new NotFoundException("Login is mislukt");
        return optionalPersoon.get();
    }
}
