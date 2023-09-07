package com.jb_cnsd.opdracht_1_2.web.dto.requests;

<<<<<<< HEAD
import org.hibernate.validator.constraints.Length;

public record PersoonCreateRequest(
        @Length(min = 9, max = 9) String bsn,
        String naam) {

=======
public record PersoonCreateRequest(String bsn, String naam) {
>>>>>>> origin/opdracht-1-4
}
