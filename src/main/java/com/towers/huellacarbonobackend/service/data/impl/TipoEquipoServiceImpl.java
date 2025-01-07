package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoEquipo;
import com.towers.huellacarbonobackend.repository.TipoEquipoRepository;
import com.towers.huellacarbonobackend.service.data.TipoEquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoEquipoServiceImpl implements TipoEquipoService {
    private final TipoEquipoRepository tipoEquipoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoEquipo> getAll() {
        return tipoEquipoRepository.findAll();
    }
}
