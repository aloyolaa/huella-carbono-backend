package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.ActividadDto;
import com.towers.huellacarbonobackend.entity.data.Actividad;

import java.util.List;

public interface ActividadService {
    List<ActividadDto> getAllByArchivo(Long archivoId);

    Actividad getByNombreAndArchivoAndSeccionAndAccion(String nombre, Long archivoId, Long seccionId, Long accionId);

    Actividad getByNombreAndArchivoAndSeccion(String nombre, Long archivoId, Long seccionId);
}
