package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;

import java.util.List;
import java.util.Optional;

public interface DataService {
    DatosGenerales save(DatosGenerales datosGenerales);

    void save(DataDto dataDto, Long empresa, Long archivo);

    DatosGenerales getById(Long id);

    Optional<DatosGenerales> getOptionalByEmpresaAndAnio(Long empresaId, Long archivo, Integer anio);

    DataDto getByEmpresaAndAnio(Long empresaId, Long archivo, Integer anio);

    DatosGenerales getByArchivoAndAnio(Long empresa, Long archivo, Integer anio);

    void deleteById(Long id);

    List<DatosGenerales> getByAnioAndEmpresa(Integer anio, Long id);
}
