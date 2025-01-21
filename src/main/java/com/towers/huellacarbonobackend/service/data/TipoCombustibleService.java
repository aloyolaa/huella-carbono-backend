package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.TipoCombustibleDto;
import com.towers.huellacarbonobackend.entity.TipoCombustible;

import java.util.List;

public interface TipoCombustibleService {
    List<TipoCombustibleDto> getAllByArchivo(Long archivoId);

    TipoCombustible getByNombreAndArchivo(String nombre, Long archivoId);
}
