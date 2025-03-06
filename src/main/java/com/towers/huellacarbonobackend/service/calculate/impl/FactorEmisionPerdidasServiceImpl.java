package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionPerdidas;
import com.towers.huellacarbonobackend.repository.FactorEmisionPerdidasRepository;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionPerdidasService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FactorEmisionPerdidasServiceImpl implements FactorEmisionPerdidasService {
    private final FactorEmisionPerdidasRepository factorEmisionPerdidasRepository;

    @Override
    @Transactional(readOnly = true)
    public FactorEmisionPerdidas getByAnio(Integer anio) {
        return factorEmisionPerdidasRepository.findByAnio(anio)
                .orElse(null);
    }
}
