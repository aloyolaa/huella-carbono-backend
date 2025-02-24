package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.TipoRefrigerante;

import java.util.List;

public interface TipoRefrigeranteService {
    List<TipoRefrigerante> getAll();

    TipoRefrigerante getByNombre(String nombre);
}
