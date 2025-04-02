package com.towers.huellacarbonobackend.dto;

public record CalculateDto(
        Double alcance1,
        String alcance1Str,
        Double alcance1Porcentaje,
        String alcance1PorcentajeStr,
        Double alcance2,
        String alcance2Str,
        Double alcance2Porcentaje,
        String alcance2PorcentajeStr,
        Double alcance3,
        String alcance3Str,
        Double alcance3Porcentaje,
        String alcance3PorcentajeStr,
        Double total,
        String totalStr
) {
}
