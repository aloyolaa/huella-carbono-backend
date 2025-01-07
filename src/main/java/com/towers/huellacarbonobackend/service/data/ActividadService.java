package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.ActividadDto;

import java.util.List;

public interface ActividadService {
    List<ActividadDto> getAllByArchivo(Long archivoId);
}
