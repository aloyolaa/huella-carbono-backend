package com.towers.huellacarbonobackend.dto;

public record ConsumoPapelDto(
        Long id,
        Long tipoHoja,
        Integer comprasAnuales,
        String unidad,
        Double reciclado,
        String certificado,
        Double densidad
) {
}
