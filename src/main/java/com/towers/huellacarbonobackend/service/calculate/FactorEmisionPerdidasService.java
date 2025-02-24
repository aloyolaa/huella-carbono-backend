package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionPerdidas;

public interface FactorEmisionPerdidasService {
    FactorEmisionPerdidas getByAnio(Integer anio);
}
