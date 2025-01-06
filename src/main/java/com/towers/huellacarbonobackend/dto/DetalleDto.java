package com.towers.huellacarbonobackend.dto;

public record DetalleDto(
        Long id,
        Long tipoCombustible,
        MesesDto meses,
        Long categoriaInstitucion,
        Long actividad,
        ClinkerDto clinker,
        RefrigeranteDto refrigerante
) {
}
