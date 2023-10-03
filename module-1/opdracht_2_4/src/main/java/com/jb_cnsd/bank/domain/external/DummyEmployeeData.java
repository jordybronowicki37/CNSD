package com.jb_cnsd.bank.domain.external;

public record DummyEmployeeData(
        int id,
        String employee_name,
        double employee_salary,
        int employee_age,
        String profile_image
) { }
