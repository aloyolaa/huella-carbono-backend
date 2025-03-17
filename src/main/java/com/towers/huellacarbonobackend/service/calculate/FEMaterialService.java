package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.entity.calculate.FEMaterial;

public interface FEMaterialService {
    FEMaterial getByTipoVehiculo(Long id);
}
