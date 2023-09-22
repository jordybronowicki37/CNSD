package com.jb_cnsd.opdracht_3_4.domain.external;

import java.util.List;

public record DummyEmployeesResponse(
        String status,
        String message,
        List<DummyEmployeeData> data
) { }
