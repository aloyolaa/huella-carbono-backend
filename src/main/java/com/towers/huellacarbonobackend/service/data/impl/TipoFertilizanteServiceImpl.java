package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoFertilizante;
import com.towers.huellacarbonobackend.repository.TipoFertilizanteRepository;
import com.towers.huellacarbonobackend.service.data.TipoFertilizanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoFertilizanteServiceImpl implements TipoFertilizanteService {
    private final TipoFertilizanteRepository tipoFertilizanteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoFertilizante> getAll() {
        return tipoFertilizanteRepository.findAll();
    }
}
