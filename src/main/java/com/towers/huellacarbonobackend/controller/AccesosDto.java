package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ArchivoDto;

import java.util.List;

public record AccesosDto(
        Boolean accesos,
        List<ArchivoDto> archivos
) {
}
