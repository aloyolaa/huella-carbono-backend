package com.towers.huellacarbonobackend.dto;

public record DisposicionDto(
        Long id,
        Long tipoEquipo,
        Long tipoRefrigerante,
        Double numeroEquipos,
        Double capacidadCarga,
        Double fraccionRefrigeranteDisposicion,
        Double fraccionRefrigeranteRecuperacion
) {
}
