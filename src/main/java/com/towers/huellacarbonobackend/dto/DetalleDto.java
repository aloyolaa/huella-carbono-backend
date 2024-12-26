package com.towers.huellacarbonobackend.dto;

public record DetalleDto(
        Long id,
        Long tipoCombustible,
        Long unidad,
        MesesDto meses,
        //Long accion,
        //Long actividad,
        Long categoriaInstitucion
        //Long produccion
) {
}
