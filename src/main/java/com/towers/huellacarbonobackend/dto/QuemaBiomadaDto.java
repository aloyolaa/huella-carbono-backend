package com.towers.huellacarbonobackend.dto;

public record QuemaBiomadaDto(
        Long id,
        Long residuoAgricola,
        Double areaCultiva,
        Double areaQuemada,
        Double produccion
) {
}
