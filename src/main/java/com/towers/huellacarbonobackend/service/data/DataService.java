package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.DatosGenerales;

import java.util.Optional;

public interface DataService {
    DatosGenerales save(DatosGenerales datosGenerales);

    void save(DataDto dataDto, Long empresa, Long archivo);

    DatosGenerales getById(Long id);

    Optional<DatosGenerales> getByEmpresaAndAnio(Long empresaId, Long archivo);

    void deleteById(Long id);
}
