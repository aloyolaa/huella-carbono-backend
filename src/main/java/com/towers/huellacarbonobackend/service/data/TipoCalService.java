package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.TipoCal;

import java.util.List;

public interface TipoCalService {
    List<TipoCal> getAll();

    TipoCal getByNombre(String nombre);
}
