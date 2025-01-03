package com.towers.huellacarbonobackend.service;

import com.towers.huellacarbonobackend.dto.TipoCombustibleDto;

import java.util.List;

public interface TipoCombustibleService {
    List<TipoCombustibleDto> getAllByArchivo(Long archivoId);
}
