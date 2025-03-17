package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FEMaterial;
import com.towers.huellacarbonobackend.repository.FEMaterialRepository;
import com.towers.huellacarbonobackend.service.calculate.FEMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FEMaterialServiceImpl implements FEMaterialService {
    private final FEMaterialRepository feMaterialRepository;

    @Override
    @Transactional(readOnly = true)
    public FEMaterial getByTipoVehiculo(Long id) {
        return feMaterialRepository.findByTipoVehiculo(id)
                .orElse(null);
    }
}
