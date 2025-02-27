package com.towers.huellacarbonobackend.dto;

public record PasswordResetDto(
        String token,
        String passwordAnterior,
        String passwordNuevo
) {
}
