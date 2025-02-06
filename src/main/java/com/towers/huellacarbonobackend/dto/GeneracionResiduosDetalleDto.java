package com.towers.huellacarbonobackend.dto;

public record GeneracionResiduosDetalleDto(
        Long id,
        Integer anio,
        Double productosMadera,
        Double productosPapel,
        Double residuos,
        Double textiles,
        Double jardines,
        Double paniales,
        Double otros
) {
}
