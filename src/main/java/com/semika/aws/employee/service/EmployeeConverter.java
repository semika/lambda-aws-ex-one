package com.semika.aws.employee.service;

import com.semika.aws.employee.model.Employee;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.util.BiConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
                .id(domain.getId())
                .first(domain.getFirst())
                .phone(domain.getFirst())
                .email(domain.getEmail())
                .startDate(domain.getStartDate())
                .build();
    }

    @Override
    public List<Employee> fromList(List<EmployeeDto> dtoList) {
        return dtoList.stream()
                .map((EmployeeDto dto) -> this.from(dto))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> toList(List<Employee> domainList) {
        return domainList.stream()
                .map((Employee employee) -> this.to(employee))
                .collect(Collectors.toList());
    }
}
