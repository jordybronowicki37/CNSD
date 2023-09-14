package com.jb_cnsd.opdracht_2_4.data.repository;

import com.jb_cnsd.opdracht_2_4.data.models.Persoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersoonRepository extends JpaRepository<Persoon, Long> {
    boolean existsByBsn(String bsn);
}
