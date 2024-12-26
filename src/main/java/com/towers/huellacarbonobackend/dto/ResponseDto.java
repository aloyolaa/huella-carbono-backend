package com.towers.huellacarbonobackend.dto;

public record ResponseDto(
        Object response,
        Boolean isSuccessfully
) {
}
