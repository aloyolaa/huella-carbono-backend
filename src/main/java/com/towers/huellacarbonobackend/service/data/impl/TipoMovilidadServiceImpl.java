package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.data.TipoMovilidad;
import com.towers.huellacarbonobackend.repository.TipoMovilidadRepository;
import com.towers.huellacarbonobackend.service.data.TipoMovilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoMovilidadServiceImpl implements TipoMovilidadService {
    private final TipoMovilidadRepository tipoMovilidadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoMovilidad> getAll() {
        return tipoMovilidadRepository.findAll();
    }
}
