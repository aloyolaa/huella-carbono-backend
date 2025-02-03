package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.Zona;

import java.util.List;

public interface ZonaService {
    List<Zona> getAll();

    Zona getByNombre(String nombre);
}
