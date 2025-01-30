package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.TipoTratamiento;

import java.util.List;

public interface TipoTratamientoService {
    List<TipoTratamiento> getAll();

    TipoTratamiento getByNombre(String nombre);
}
