package com.towers.huellacarbonobackend.dto;

public record FertilizanteDto(
        Long id,
        Long tipoFertilizante,
        Long residuo,
        Double contenidoNitrogeno,
        Double cantidadEmpleada
) {
}
