package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionCombustible;

import java.util.List;

public interface FactorEmisionCombustibleService {
    FactorEmisionCombustible getByTipoCombustible(Long id);

    List<FactorEmisionCombustible> getAll();
}
