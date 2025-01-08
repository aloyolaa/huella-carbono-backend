package com.towers.huellacarbonobackend.dto;

public record GanadoDto(
        Long id,
        Long tipoAnimal,
        Long tipoTratamiento,
        Double pesoPromedioAnimal,
        Integer cantidadAnualAnimales
) {
}
