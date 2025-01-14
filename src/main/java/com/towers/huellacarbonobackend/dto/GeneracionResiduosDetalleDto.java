package com.towers.huellacarbonobackend.dto;

public record GeneracionResiduosDetalleDto(
        Long id,
        Integer anio,
        Integer productosMadera,
        Integer productosPapel,
        Integer residuos,
        Integer textiles,
        Integer jardines,
        Integer paniales,
        Integer otros
) {
}
