package com.jb_cnsd.bank.data.repository;

import com.jb_cnsd.bank.data.models.Persoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersoonRepository extends JpaRepository<Persoon, Long> {
    boolean existsByBsn(String bsn);
}
