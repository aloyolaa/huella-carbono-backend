package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FEVehiculo;
import com.towers.huellacarbonobackend.repository.FEVehiculoRepository;
import com.towers.huellacarbonobackend.service.calculate.FEVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FEVehiculoServiceImpl implements FEVehiculoService {
    private final FEVehiculoRepository feVehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public FEVehiculo getByTipoTransporte(Long id) {
        return feVehiculoRepository.findByTipoTransporte(id)
                .orElse(null);
    }
}
