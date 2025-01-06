package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.ActividadDto;
import com.towers.huellacarbonobackend.entity.Actividad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActividadMapper {

    public ActividadDto toActividadDto(Actividad actividad) {
        return new ActividadDto(
                actividad.getId(),
                actividad.getNombre(),
                actividad.getProduccion().getNombre(),
                actividad.getAccion().getNombre()
        );
    }
}
