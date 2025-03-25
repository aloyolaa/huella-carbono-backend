package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionCombustible;
import com.towers.huellacarbonobackend.repository.FactorEmisionCombustibleRepository;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional(readOnly = true)
    public List<FactorEmisionCombustible> getAll() {
        return factorEmisionCombustibleRepository.findAll();
    }
}
