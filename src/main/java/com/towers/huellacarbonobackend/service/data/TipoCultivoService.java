package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.TipoCultivo;

import java.util.List;

public interface TipoCultivoService {
    List<TipoCultivo> getAll();

    TipoCultivo getByNombre(String nombre);
}
