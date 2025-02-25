package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.entity.data.Empresa;

import java.util.List;

public interface EmpresaService {
    Empresa getById(Long id);

    //List<ArchivoDto> getArchivosByAnio(Long id, Integer anio);
}
