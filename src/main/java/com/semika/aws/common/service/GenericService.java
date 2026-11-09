package com.semika.aws.common.service;

import java.util.List;

public interface GenericService <DOMAIN, DTO> {
    DTO findById(String id);
    List<DTO> findAll();
    DTO save(DTO dto);
    DTO update(DTO dto);
}
