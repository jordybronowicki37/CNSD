package com.jb_cnsd.opdracht_2_4.domain.external;

public record DummyEmployeeResponse(
        String status,
        String message,
        DummyEmployeeData data
) { }