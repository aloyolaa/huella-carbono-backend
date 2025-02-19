package com.towers.huellacarbonobackend.dto;

public record GeneracionResiduosDataDto(
        Long id,
        Integer anioHuella,
        Double precipitacion,
        Integer anioInicio,
        Double temperatura,
        Boolean contenidoGrasas,
        Double tasaCrecimiento,
        Long condicionSEDS
) {
}
