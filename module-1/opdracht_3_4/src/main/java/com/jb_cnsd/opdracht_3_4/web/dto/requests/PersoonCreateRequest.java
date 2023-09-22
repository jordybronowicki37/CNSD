package com.jb_cnsd.opdracht_3_4.web.dto.requests;

import org.hibernate.validator.constraints.Length;

public record PersoonCreateRequest(
        @Length(min = 9, max = 9) String bsn,
        String naam) {

}
