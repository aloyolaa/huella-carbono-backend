package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.TipoFertilizante;

import java.util.List;

public interface TipoFertilizanteService {
    List<TipoFertilizante> getAll();

    TipoFertilizante getByNombre(String nombre);
}
