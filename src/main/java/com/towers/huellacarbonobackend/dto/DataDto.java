package com.towers.huellacarbonobackend.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DataDto(
        Long id,
        @NotBlank(message = "{NotBlank.datosGenerales.nombre}")
        String nombre,
        @NotBlank(message = "{NotBlank.datosGenerales.cargo}")
        String cargo,
        @NotBlank(message = "{NotBlank.datosGenerales.correo}")
        String correo,
        @NotBlank(message = "{NotBlank.datosGenerales.locacion}")
        String locacion,
        String comentarios,
        GanadoDataDto ganadoData,
        GeneracionResiduosDataDto generacionResiduosData,
        List<DetalleDto> detalles
) {
}
