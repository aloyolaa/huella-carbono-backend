package com.towers.huellacarbonobackend.dto;

import java.util.List;

public record AccesosDto(
        Boolean accesos,
        List<ArchivoDto> archivos
) {
}
