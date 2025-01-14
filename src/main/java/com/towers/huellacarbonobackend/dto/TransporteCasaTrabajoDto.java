package com.towers.huellacarbonobackend.dto;

public record TransporteCasaTrabajoDto(
        Long id,
        String descripcionPersonal,
        Integer trabajadores,
        Integer viajesSemanales,
        Integer diasLaborales,
        Double distanciaViaje,
        Long tipoMovilidad
) {
}
