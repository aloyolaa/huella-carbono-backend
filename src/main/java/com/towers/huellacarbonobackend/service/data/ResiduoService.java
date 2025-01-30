package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.Residuo;

import java.util.List;

public interface ResiduoService {
    List<Residuo> getAll();

    Residuo getByNombre(String nombre);
}
