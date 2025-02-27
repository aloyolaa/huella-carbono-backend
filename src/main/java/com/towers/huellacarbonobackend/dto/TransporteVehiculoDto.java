package com.towers.huellacarbonobackend.dto;

public record TransporteVehiculoDto(
        Long id,
        String tramo,
        Double distanciaRecorrida,
        Integer personasViajando,
        Integer vecesRecorrido,
        Long tipoTransporte
) {
}
