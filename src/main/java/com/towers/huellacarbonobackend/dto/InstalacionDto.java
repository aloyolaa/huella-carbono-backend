package com.towers.huellacarbonobackend.dto;

public record InstalacionDto(
        Long id,
        Long tipoEquipo,
        Long tipoRefrigerante,
        Double numeroEquipos,
        Double capacidadCarga,
        Double fugaInstalacion
) {
}
