package com.semika.aws.util;

import java.util.List;

public interface DomainToDtoListConverter<DOMAIN, DTO> {
    List<DTO> toList(List<DOMAIN> domainList);
}
