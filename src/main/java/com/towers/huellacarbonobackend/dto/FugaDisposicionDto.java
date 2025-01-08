package com.towers.huellacarbonobackend.dto;

public record FugaDisposicionDto(
        Long id,
        String descripcionEquipo,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double fraccionSF6Disposicion,
        Double fraccionSF6Recuperado
) {
}
