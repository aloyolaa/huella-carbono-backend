package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.TipoVehiculo;

import java.util.List;

public interface TipoVehiculoService {
    List<TipoVehiculo> getAll();

    TipoVehiculo getByNombre(String nombre);
}
