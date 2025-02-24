package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionCombustible;

public interface FactorEmisionCombustibleService {
    FactorEmisionCombustible getByTipoCombustible(Long id);
}
