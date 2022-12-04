package com.semika.aws.employee.service;

import com.semika.aws.employee.model.Employee;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.util.BiConverter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmployeeConverter implements BiConverter<Employee, EmployeeDto> {

    @Override
    public Employee dtoToDomain(EmployeeDto dto) {
        return Employee.builder()
                .id(UUID.randomUUID().toString())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .startDate(dto.getStartDate())
                .first(dto.getFirst())
                .build();
    }

    @Override
    public EmployeeDto domainToDto(Employee domain) {
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
                .map((EmployeeDto dto) -> this.dtoToDomain(dto))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> toList(List<Employee> domainList) {
        return domainList.stream()
                .map((Employee employee) -> this.domainToDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public Employee dtoToDomain(EmployeeDto employeeDto, Employee employee) {
        employee.setFirst(employeeDto.getFirst());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setStartDate(employeeDto.getStartDate());
        return employee;
    }
}
