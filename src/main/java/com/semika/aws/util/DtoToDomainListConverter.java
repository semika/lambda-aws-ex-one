package com.semika.aws.util;

import java.util.List;

@FunctionalInterface
public interface DtoToDomainListConverter<DOMAIN, DTO> {
    List<DOMAIN> fromList(List<DTO> dtoList);
}
