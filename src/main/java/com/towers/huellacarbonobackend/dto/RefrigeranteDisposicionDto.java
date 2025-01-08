package com.towers.huellacarbonobackend.dto;

public record RefrigeranteDisposicionDto(
        Long id,
        Long tipoEquipo,
        Long tipoRefrigerante,
        Integer numeroEquipos,
        Double capacidadCarga,
        Double fraccionRefrigeranteDisposicion,
        Double fraccionRefrigeranteRecuperacion
) {
}
