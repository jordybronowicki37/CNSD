package com.jb_cnsd.bank.domain.external;

import java.util.List;

public record DummyEmployeesResponse(
        String status,
        String message,
        List<DummyEmployeeData> data
) { }
