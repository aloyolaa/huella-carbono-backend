package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FactorConversionCombustible;
import com.towers.huellacarbonobackend.repository.FactorConversionCombustibleRepository;
import com.towers.huellacarbonobackend.service.calculate.FactorConversionCombustibleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FactorConversionCombustibleServiceImpl implements FactorConversionCombustibleService {
    private final FactorConversionCombustibleRepository factorConversionCombustibleRepository;

    @Override
    @Transactional(readOnly = true)
    public FactorConversionCombustible getByTipoCombustible(Long id) {
        return factorConversionCombustibleRepository.findByTipoCombustible(id)
                .orElse(null);
    }
}
