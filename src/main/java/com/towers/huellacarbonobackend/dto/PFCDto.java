package com.towers.huellacarbonobackend.dto;

public record PFCDto(
        PFCInstalacionDto instalacion,
        PFCOperacionDto operacion,
        PFCDisposicionDto disposicion
) {
}
