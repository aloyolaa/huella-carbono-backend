package com.towers.huellacarbonobackend.dto;

public record UsuarioRegisterDto(
        String username,
        String password,
        String nombre,
        String apellido,
        String correo,
        Long empresa
) {
}
