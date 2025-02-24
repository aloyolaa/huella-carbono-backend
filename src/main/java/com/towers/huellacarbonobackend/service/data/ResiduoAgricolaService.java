package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.ResiduoAgricola;

import java.util.List;

public interface ResiduoAgricolaService {
    List<ResiduoAgricola> getAll();

    ResiduoAgricola getByNombre(String nombre);
}
