package com.jb_cnsd.bank.domain.external;

public record DummyEmployeeResponse(
        String status,
        String message,
        DummyEmployeeData data
) { }
