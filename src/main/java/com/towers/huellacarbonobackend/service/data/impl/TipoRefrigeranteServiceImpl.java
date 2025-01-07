package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoRefrigerante;
import com.towers.huellacarbonobackend.repository.TipoRefrigeranteRepository;
import com.towers.huellacarbonobackend.service.data.TipoRefrigeranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoRefrigeranteServiceImpl implements TipoRefrigeranteService {
    private final TipoRefrigeranteRepository tipoRefrigeranteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoRefrigerante> getAll() {
        return tipoRefrigeranteRepository.findAll();
    }
}
