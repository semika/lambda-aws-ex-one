package com.semika.aws.util;

public interface BiConverter <DOMAIN, DTO>
        extends DtoToDomainConverter<DOMAIN, DTO>, DomainToDtoConverter<DOMAIN, DTO>,
        DtoToDomainListConverter<DOMAIN, DTO>, DomainToDtoListConverter<DOMAIN, DTO> {

}
