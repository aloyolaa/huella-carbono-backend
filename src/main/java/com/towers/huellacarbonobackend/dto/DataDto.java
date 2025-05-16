package com.towers.huellacarbonobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
        @NotNull(message = "{NotNull.datosGenerales.anio}")
        Integer anio,
        @NotNull(message = "{NotNull.datosGenerales.mes}")
        Integer mes,
        String emision,
        GanadoDataDto ganadoData,
        @Size(message = "{Size.datosGenerales.detalles}")
        List<DetalleDto> detalles
) {
}
