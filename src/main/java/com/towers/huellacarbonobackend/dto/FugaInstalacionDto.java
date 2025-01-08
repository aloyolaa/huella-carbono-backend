package com.towers.huellacarbonobackend.dto;

public record FugaInstalacionDto(
        Long id,
        String descripcionEquipo,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double fugaInstalacion
) {
}
