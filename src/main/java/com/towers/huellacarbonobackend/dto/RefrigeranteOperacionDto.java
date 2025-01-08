package com.towers.huellacarbonobackend.dto;

public record RefrigeranteOperacionDto(
        Long id,
        Long tipoEquipo,
        Long tipoRefrigerante,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double anio,
        Double fugaUso
) {
}
