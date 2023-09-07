package com.jb_cnsd.opdracht_1_2.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Persoon extends BaseEntity {
    @Column(unique = true, length = 9, columnDefinition = "CHAR(9)")
    private final String bsn;

    @Column(length = 64)
    private String naam;

    @ManyToMany(mappedBy = "personen")
    private final Set<Rekening> rekeningen;

    protected Persoon() {
        this.bsn = "";
        rekeningen = new HashSet<>();
    }

    public Persoon(String bsn, String naam) {
        this.bsn = bsn;
        this.naam = naam;
        rekeningen = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persoon that = (Persoon) o;
        return Objects.equals(bsn, that.bsn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bsn);
    }
}
