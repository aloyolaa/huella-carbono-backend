package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.ActividadDto;
import com.towers.huellacarbonobackend.entity.data.Actividad;
import org.springframework.stereotype.Service;

@Service
public class ActividadMapper {

    public ActividadDto toActividadDto(Actividad actividad) {
        return new ActividadDto(
                actividad.getId(),
                actividad.getNombre(),
                actividad.getProduccion().getNombre(),
                actividad.getAccion().getNombre(),
                actividad.getSeccion() != null ? actividad.getSeccion().getId() : null,
                actividad.getSeccion() != null ? actividad.getSeccion().getNombre() : null
        );
    }
}
