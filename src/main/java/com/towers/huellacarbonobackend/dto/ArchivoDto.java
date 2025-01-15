package com.towers.huellacarbonobackend.dto;

public record ArchivoDto(
        Long id,
        String clave,
        String nombre,
        Long alcance,
        String alcanceNombre
) {
}
