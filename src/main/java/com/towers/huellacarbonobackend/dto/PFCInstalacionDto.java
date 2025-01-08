package com.towers.huellacarbonobackend.dto;

public record PFCInstalacionDto(
        Long id,
        String descripcionEquipo,
        Long tipoPFC,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double fugaInstalacion
) {
}
