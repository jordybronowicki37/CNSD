package com.jb_cnsd.opdracht_2_2.domain.external;

import java.util.List;

public record DummyEmployeeResponse(
        String status,
        String message,
        DummyEmployeeData data
) { }
