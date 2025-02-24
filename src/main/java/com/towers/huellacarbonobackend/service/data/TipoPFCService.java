package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.TipoPFC;

import java.util.List;

public interface TipoPFCService {
    List<TipoPFC> getAll();

    TipoPFC getByNombre(String nombre);
}
