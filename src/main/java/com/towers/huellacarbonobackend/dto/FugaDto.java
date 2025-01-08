package com.towers.huellacarbonobackend.dto;

public record FugaDto(
        FugaInstalacionDto instalacion,
        FugaOperacionDto operacion,
        FugaDisposicionDto disposicion
) {
}
