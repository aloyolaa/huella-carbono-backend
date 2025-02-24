package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.TipoHoja;

import java.util.List;

public interface TipoHojaService {
    List<TipoHoja> getAll();

    TipoHoja getTipoHojaByNombre(String nombre);
}
