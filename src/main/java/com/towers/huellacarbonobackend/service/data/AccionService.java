package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.Accion;

public interface AccionService {
    Accion getByNombre(String nombre);
}
