package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionCombustible;
import com.towers.huellacarbonobackend.repository.FactorEmisionCombustibleRepository;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionCombustibleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FactorEmisionCombustibleServiceImpl implements FactorEmisionCombustibleService {
    private final FactorEmisionCombustibleRepository factorEmisionCombustibleRepository;

    @Override
    @Transactional(readOnly = true)
    public FactorEmisionCombustible getByTipoCombustible(Long id) {
        return factorEmisionCombustibleRepository.findByTipoCombustible(id)
                .orElse(null);
    }
}
