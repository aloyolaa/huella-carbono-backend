package com.towers.huellacarbonobackend.dto;

public record LoginResponse(
        String message,
        Boolean esNuevo,
        String token
) {
}
