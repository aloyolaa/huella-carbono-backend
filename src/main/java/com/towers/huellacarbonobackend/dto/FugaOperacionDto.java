package com.towers.huellacarbonobackend.dto;

public record FugaOperacionDto(
        Long id,
        String descripcionEquipo,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double tiempoUso,
        Double fugaUso
) {
}
