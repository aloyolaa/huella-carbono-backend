package com.towers.huellacarbonobackend.dto;

public record EmbalseDto(
        Long id,
        String nombre,
        String ubicacion,
        Long zona,
        Double area,
        Integer periodoLibreHielo,
        Double fraccionAreaInundada
) {
}
