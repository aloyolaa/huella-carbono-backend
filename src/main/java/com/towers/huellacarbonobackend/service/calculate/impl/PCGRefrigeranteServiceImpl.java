package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.PCGRefrigerante;
import com.towers.huellacarbonobackend.repository.PCGRefrigeranteRepository;
import com.towers.huellacarbonobackend.service.calculate.PCGRefrigeranteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PCGRefrigeranteServiceImpl implements PCGRefrigeranteService {
    private final PCGRefrigeranteRepository pcgRefrigeranteRepository;

    @Override
    @Transactional(readOnly = true)
    public PCGRefrigerante getByTipoRefrigerante(Long id) {
        return pcgRefrigeranteRepository.findByTipoRefrigerante(id)
                .orElse(null);
    }
}
