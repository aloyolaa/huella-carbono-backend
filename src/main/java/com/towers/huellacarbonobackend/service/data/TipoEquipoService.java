package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.TipoEquipo;

import java.util.List;

public interface TipoEquipoService {
    List<TipoEquipo> getAll();

    TipoEquipo getByNombre(String nombre);
}
