package com.towers.huellacarbonobackend.dto;

public record CultivoArrozDto(
        Long id,
        Long tipoCultivo,
        Integer periodoCultivo,
        Double areaCultivo,
        Long tipoFertilizante,
        Long residuo,
        Double contenidoNitrogeno,
        Double cantidadEmpleada
) {
}
