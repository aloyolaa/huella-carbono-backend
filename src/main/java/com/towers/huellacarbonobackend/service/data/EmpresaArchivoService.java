package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.ArchivoDto;

import java.util.List;

public interface EmpresaArchivoService {
    void save(List<Integer> archivos, Long id, Integer anio);

    List<ArchivoDto> getArchivosByAnio(Long id, Integer anio);

    boolean existsByEmpresa(Long id);

    List<Integer> getAniosByEmpresa(Long id);
}
