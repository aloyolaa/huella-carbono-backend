package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.TipoSuelo;

import java.util.List;

public interface TipoSueloService {
    List<TipoSuelo> getAll();

    TipoSuelo getByNombre(String nombre);
}
