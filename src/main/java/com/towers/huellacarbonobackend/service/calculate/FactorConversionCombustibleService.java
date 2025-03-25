package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.entity.calculate.FactorConversionCombustible;

import java.util.List;

public interface FactorConversionCombustibleService {
    FactorConversionCombustible getByTipoCombustible(Long id);

    List<FactorConversionCombustible> getAll();
}
