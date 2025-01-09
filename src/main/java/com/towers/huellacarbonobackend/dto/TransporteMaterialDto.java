package com.towers.huellacarbonobackend.dto;

public record TransporteMaterialDto(
        Long id,
        String descripcion,
        Integer viajes,
        String tramo,
        Double pesoTransportado,
        Double distanciaRecorrida,
        Long tipoVehiculo
) {
}
