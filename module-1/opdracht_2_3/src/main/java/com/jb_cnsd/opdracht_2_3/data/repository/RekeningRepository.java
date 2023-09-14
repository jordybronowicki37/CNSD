package com.jb_cnsd.opdracht_2_3.data.repository;

import com.jb_cnsd.opdracht_2_3.data.models.Iban;
import com.jb_cnsd.opdracht_2_3.data.models.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RekeningRepository extends JpaRepository<Rekening, Long> {
    boolean existsByIban(Iban iban);
}
