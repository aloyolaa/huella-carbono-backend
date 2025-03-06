package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.entity.calculate.FEVehiculo;

public interface FEVehiculoService {
    FEVehiculo getByTipoTransporte(Long id);
}
