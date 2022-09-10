package com.semika.aws.employee.service;

import com.semika.aws.employee.model.Employee;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.util.BiConverter;

import java.util.UUID;

public class EmployeeConverter implements BiConverter<Employee, EmployeeDto> {

    @Override
    public Employee from(EmployeeDto dto) {
        return Employee.builder()
                .id(UUID.randomUUID().toString())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .startDate(dto.getStartDate())
                .first(dto.getFirst())
                .build();
    }

    @Override
    public EmployeeDto to(Employee domain) {
        return EmployeeDto.builder()
                .Id(domain.getId())
                .first(domain.getFirst())
                .phone(domain.getFirst())
                .email(domain.getEmail())
                .startDate(domain.getStartDate())
                .build();
    }
}
