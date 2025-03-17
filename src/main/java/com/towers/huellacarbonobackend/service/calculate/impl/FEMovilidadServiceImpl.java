package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FEMovilidad;
import com.towers.huellacarbonobackend.repository.FEMovilidadRepository;
import com.towers.huellacarbonobackend.service.calculate.FEMovilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FEMovilidadServiceImpl implements FEMovilidadService {
    private final FEMovilidadRepository feMovilidadRepository;

    @Override
    @Transactional(readOnly = true)
    public FEMovilidad getByTipoMovilidad(Long id) {
        return feMovilidadRepository.findByTipoMovilidad(id)
                .orElse(null);
    }
}
