package com.jb_cnsd.opdracht_3_4.domain.external;

public record DummyEmployeeResponse(
        String status,
        String message,
        DummyEmployeeData data
) { }
