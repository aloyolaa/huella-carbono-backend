package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionConsumo;
import com.towers.huellacarbonobackend.repository.FactorEmisionConsumoRepository;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionConsumoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FactorEmisionConsumoServiceImpl implements FactorEmisionConsumoService {
    private final FactorEmisionConsumoRepository factorEmisionConsumoRepository;

    @Override
    @Transactional(readOnly = true)
    public FactorEmisionConsumo getByAnio(Integer anio) {
        return factorEmisionConsumoRepository.findByAnio(anio)
                .orElseThrow(() -> new EntityNotFoundException("No hay Factores de Emisión de Consumo para el año " + anio));
    }
}
