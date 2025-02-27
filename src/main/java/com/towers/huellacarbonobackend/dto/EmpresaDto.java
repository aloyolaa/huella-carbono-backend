package com.towers.huellacarbonobackend.dto;

public record EmpresaDto(
        String razonSocial,
        String ruc,
        String direccion,
        Double telefono,
        String correo
) {
}
