package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.CategoriaInstitucion;

import java.util.List;

public interface CategoriaInstitucionService {
    List<CategoriaInstitucion> getAll();

    CategoriaInstitucion getByNombre(String nombre);
}
