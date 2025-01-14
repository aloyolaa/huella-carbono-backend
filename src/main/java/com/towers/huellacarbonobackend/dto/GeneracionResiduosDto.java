package com.towers.huellacarbonobackend.dto;

import java.util.List;

public record GeneracionResiduosDto(
        Long id,
        Integer anioHuella,
        Double precipitacion,
        Integer anioInicio,
        Double temperatura,
        Boolean contenidoGrasas,
        Double tasaCrecimiento,
        Long condicionSEDS,
        List<GeneracionResiduosDetalleDto> generacionResiduosDetalles
) {
}
