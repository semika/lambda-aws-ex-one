package com.semika.aws.util;

public interface DomainToDtoConverter<DOMAIN, DTO> {
    DTO domainToDto(DOMAIN domain);

    default DTO domainToDto(DOMAIN domain, DTO dto) {
        throw new RuntimeException("Not supported");
    }

}
