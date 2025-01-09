package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoTransporte;
import com.towers.huellacarbonobackend.repository.TipoTransporteRepository;
import com.towers.huellacarbonobackend.service.data.TipoTransporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoTransporteServiceImpl implements TipoTransporteService {
    private final TipoTransporteRepository tipoTransporteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoTransporte> getAll() {
        return tipoTransporteRepository.findAll();
    }
}
