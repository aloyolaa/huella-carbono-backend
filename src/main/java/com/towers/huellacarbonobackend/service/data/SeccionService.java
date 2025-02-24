package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.Seccion;

import java.util.Optional;

public interface SeccionService {
    Optional<Seccion> getOptionalByNombre(String nombre);

    Optional<Seccion> getOptionalByNombreContains(String nombre);
}
