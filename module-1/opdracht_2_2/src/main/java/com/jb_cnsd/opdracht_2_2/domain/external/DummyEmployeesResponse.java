package com.jb_cnsd.opdracht_2_2.domain.external;

import java.util.List;

public record DummyEmployeesResponse(
        String status,
        String message,
        List<DummyEmployeeData> data
) { }
