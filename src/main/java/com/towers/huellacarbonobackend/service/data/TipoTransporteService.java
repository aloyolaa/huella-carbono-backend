package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.TipoTransporte;

import java.util.List;

public interface TipoTransporteService {
    List<TipoTransporte> getAll();

    TipoTransporte getByNombre(String nombre);
}
