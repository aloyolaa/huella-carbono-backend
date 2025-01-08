package com.towers.huellacarbonobackend.dto;

public record RefrigeranteInstalacionDto(
        Long id,
        Long tipoEquipo,
        Long tipoRefrigerante,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double fugaInstalacion
) {
}
