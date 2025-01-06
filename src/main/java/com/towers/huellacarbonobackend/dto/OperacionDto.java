package com.towers.huellacarbonobackend.dto;

public record OperacionDto(
        Long id,
        Long tipoEquipo,
        Long tipoRefrigerante,
        Double numeroEquipos,
        Double capacidadCarga,
        Double anio,
        Double fugaUso
) {
}
