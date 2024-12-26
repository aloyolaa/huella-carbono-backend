package com.towers.huellacarbonobackend.dto;

import java.util.List;

public record DataDto(
        Long id,
        String nombre,
        String cargo,
        String correo,
        String locacion,
        String comentarios,
        List<DetalleDto> detalles
) {
}
