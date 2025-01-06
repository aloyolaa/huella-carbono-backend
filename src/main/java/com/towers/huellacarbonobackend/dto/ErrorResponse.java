package com.towers.huellacarbonobackend.dto;

public record ErrorResponse(
        String title,
        Object message
) {
}
