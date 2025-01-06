package com.towers.huellacarbonobackend.dto;

public record ClinkerDto(
        Long id,
        String cemento,
        Double produccionCemento,
        Double produccionClinker,
        Double contenidoCaOClinker,
        Double contenidoCaOCaCO3
) {
}
