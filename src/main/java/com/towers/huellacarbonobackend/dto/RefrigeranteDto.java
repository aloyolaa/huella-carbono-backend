package com.towers.huellacarbonobackend.dto;

public record RefrigeranteDto(
        RefrigeranteInstalacionDto instalacion,
        RefrigeranteOperacionDto operacion,
        RefrigeranteDisposicionDto disposicion
) {
}
