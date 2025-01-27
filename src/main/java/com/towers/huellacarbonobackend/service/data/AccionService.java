package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.Accion;

public interface AccionService {
    Accion getByNombre(String nombre);
}
