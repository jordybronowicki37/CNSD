package com.jb_cnsd.opdracht_5_1.domain.external;

public record DummyEmployeeResponse(
        String status,
        String message,
        DummyEmployeeData data
) { }
