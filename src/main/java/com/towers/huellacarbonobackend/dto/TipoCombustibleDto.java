package com.towers.huellacarbonobackend.dto;

public record TipoCombustibleDto(
        Long id,
        String nombre,
        String unidad,
        Long seccionId,
        String seccion
) {
}
