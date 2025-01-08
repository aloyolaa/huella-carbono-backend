package com.towers.huellacarbonobackend.dto;

public record PFCDisposicionDto(
        Long id,
        String descripcionEquipo,
        Long tipoPFC,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double fraccionGasPFCDisposicion,
        Double fraccionGasPFCRecuperado
) {
}
