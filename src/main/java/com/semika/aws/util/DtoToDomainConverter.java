package com.semika.aws.util;

@FunctionalInterface
public interface DtoToDomainConverter<DOMAIN, DTO> {
    DOMAIN dtoToDomain(DTO dto);

    default  DOMAIN dtoToDomain(DTO dto, DOMAIN domain) {
        throw new RuntimeException("Not supported");
    }
}
