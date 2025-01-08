package com.towers.huellacarbonobackend.dto;

public record PFCOperacionDto(
        Long id,
        String descripcionEquipo,
        Long tipoPFC,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double tiempoUso,
        Double fugaUso
) {
}
